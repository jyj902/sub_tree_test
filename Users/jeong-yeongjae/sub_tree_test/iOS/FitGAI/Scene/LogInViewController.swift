//
//  LogInViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/06.
//

import UIKit
import Firebase
import GoogleSignIn
import KakaoSDKUser
import NaverThirdPartyLogin
import RxSwift
import AuthenticationServices

// MARK: - Public Outter Class, Struct, Enum, Protocol

fileprivate enum DebugStep {
    case none
    case step1
    case step2
}

class LogInViewController: BaseViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    @IBOutlet weak var lbWelcome: UILabel!
    @IBOutlet weak var btnGoogle: LoginButton!
    @IBOutlet weak var btnKakao: LoginButton!
    @IBOutlet weak var btnNaver: LoginButton!
    @IBOutlet weak var btnApple: LoginButton!
    @IBOutlet weak var btnDeveloper: LoginButton!
    @IBOutlet weak var btnSample: LoginButton!
    // MARK: - Private Variable
    private var kakaoRepository:KakaoRepository?
    private var naverRepository:NaverRepository?
    private var disposeBag = DisposeBag()
    private var debugStep:DebugStep = .none
    
    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeViews()
        kakaoRepository = KakaoRepository(requestNetworkSubject: nil)
        naverRepository = NaverRepository(requestNetworkSubject: nil)
        NaverThirdPartyLoginConnection.getSharedInstance().delegate = self
        
        debugStep = .none
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    private func initializeViews() {
        lbWelcome.font = UIFont.typeA34
        lbWelcome.textColor = UIColor.fontDarkPrimary
        
        btnGoogle.backgroundColor = UIColor.bgGoogleLogin
        btnGoogle.setTitleColor(UIColor.fontDarkPrimary, for: .normal)
        btnGoogle.setImage(R.image.account_ic_login_google_nor()?.withRenderingMode(.alwaysOriginal), for: .normal)
        
        btnKakao.backgroundColor = UIColor.bgKakaoLogin
        btnKakao.setTitleColor(UIColor.fontDarkPrimary, for: .normal)
        btnKakao.setImage(R.image.account_ic_login_kakao_nor()?.withRenderingMode(.alwaysOriginal), for: .normal)
        
        btnNaver.backgroundColor = UIColor.bgNaverLogin
        btnNaver.setTitleColor(UIColor.fontDarkPrimary, for: .normal)
        btnNaver.setImage(R.image.account_ic_login_naver_nor()?.withRenderingMode(.alwaysOriginal), for: .normal)
        
        btnApple.backgroundColor = UIColor.bgAppleLogin
        btnApple.setTitleColor(UIColor.fontLightPrimary, for: .normal)
        btnApple.setImage(R.image.account_ic_login_apple_nor()?.withRenderingMode(.alwaysOriginal), for: .normal)

        btnDeveloper.backgroundColor = UIColor.bgDeveloperLogin
        btnDeveloper.setTitleColor(UIColor.fontDarkPrimary, for: .normal)
        btnDeveloper.setImage(R.image.account_ic_login_developer()?.withRenderingMode(.alwaysOriginal), for: .normal)
        
        btnSample.backgroundColor = UIColor.bgSampleLogin
        btnSample.setTitleColor(UIColor.fontDarkPrimary, for: .normal)
        btnSample.setImage(R.image.sample()?.withRenderingMode(.alwaysOriginal), for: .normal)
        
        if FeatureConst.FEATURE_DEVELOPER_LOGIN {
            btnDeveloper.isHidden = false
        } else {
            btnDeveloper.isHidden = true
        }
        
        #if DEBUG
        btnSample.isHidden = false
        #else
        btnSample.isHidden = true
        #endif
    }
    
    // MARK: - IBAction
    @IBAction func onClickGoogleLogin(_ sender: Any) {
        guard let clientID = FirebaseApp.app()?.options.clientID else { return }
        
        // Create Google Sign In configuration object.
        let config = GIDConfiguration(clientID: clientID)
        
        // Start the sign in flow!
        GIDSignIn.sharedInstance.signIn(with: config, presenting: self) { [weak self] user, error in
            if let error = error {
                HHLog.e(.login, .no0047, "ERROR = \(error.localizedDescription)")
                return
            }
            
            guard let authentication = user?.authentication,
                let idToken = authentication.idToken
            else {
                HHLog.e(.login, .no0048, "ERROR authentication, idToken is nil")
                return
            }
            HHLog.d(.login, "user = \(user), authentication = \(authentication), error = \(error)")
            AccountManager.shared.loginType = LoginTypeEnum.google.rawValue
            AccountManager.shared.idTokenGoogle = authentication.idToken
            AccountManager.shared.accessToken3rd = authentication.accessToken
            AccountManager.shared.refreshToken3rd = authentication.refreshToken
            AccountManager.shared.userEmail = user?.profile?.email
            AccountManager.shared.userName = user?.profile?.name

            let _ = GoogleAuthProvider.credential(withIDToken: idToken, accessToken: authentication.accessToken)
            AccountManager.shared.processLogin()
        }
    }
    
    @IBAction func onClickKakaoLogin(_ sender: Any) {
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk { [weak self] oauthToken, error in
                HHLog.d(.login, "oauthToken = \(oauthToken), error = \(error)")
                AccountManager.shared.loginType = LoginTypeEnum.kakao.rawValue
                AccountManager.shared.accessToken3rd = oauthToken?.accessToken
                AccountManager.shared.refreshToken3rd = oauthToken?.refreshToken
                
                if let accessToken = oauthToken?.accessToken,
                   let self = self {
                    self.kakaoRepository?.getUserInfo(accessToken: accessToken)
                        .subscribe(onSuccess: { result in
                            HHLog.d(.network, "result = \(result)")
                            AccountManager.shared.userEmail = result.email
                            AccountManager.shared.userName = result.name
                            AccountManager.shared.processLogin()
                        }, onFailure: { error in
                            HHLog.e(.login, .no0049, "kakao login fail")
                            HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO(""), contentsText: TODO("카카오 로그인에 실패했습니다."), okText: TODO("확인"))
                        }).disposed(by: self.disposeBag)
                }
            }
        } else {
            HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO(""), contentsText: TODO("카카오 앱이 설치 되어있지 않습니다."), okText: TODO("확인"))
        }
    }
    
    @IBAction func onClickNaverLogin(_ sender: Any) {
        switch NaverThirdPartyLoginConnection.getSharedInstance().state() {
        case NEED_INIT, NEED_LOGIN:
            HHLog.d(.login, "case NEED_REFRESH_ACCESS_TOKEN, OK:")
            NaverThirdPartyLoginConnection.getSharedInstance().requestThirdPartyLogin()
        case NEED_REFRESH_ACCESS_TOKEN, OK:
            HHLog.e(.login, .no0050, "case NEED_REFRESH_ACCESS_TOKEN, OK:")
            NaverThirdPartyLoginConnection.getSharedInstance().requestDeleteToken()
            NaverThirdPartyLoginConnection.getSharedInstance().requestThirdPartyLogin()
        default:
            HHLog.e(.login, .no0051, "state is nothing")
        }
    }
    
    @IBAction func onClickAppleLogin(_ sender: Any) {
        let request = ASAuthorizationAppleIDProvider().createRequest()
        request.requestedScopes = [.fullName, .email]
        let controller = ASAuthorizationController(authorizationRequests: [request])
        controller.delegate = self
        controller.presentationContextProvider = self as? ASAuthorizationControllerPresentationContextProviding
        controller.performRequests()
    }
    
    @IBAction func onClickDeveloper(_ sender: Any) {
        AccountManager.shared.loginType = LoginTypeEnum.developer.rawValue
        AccountManager.shared.accessToken = "developer_accessToken"
        AccountManager.shared.id = 0
        AccountManager.shared.userEmail = "developer@dev.com"
        AccountManager.shared.userName = "developer"
        RealmManager.shared.configurationDefaultRealm(loginType:LoginTypeEnum.developer ,userNo: AccountManager.shared.userEmail)
        let vc = UIViewController.getViewController(viewControllerEnum: .main)
        UINavigationController.setRootViewController(viewController: vc, withNavigationController: true)
    }
    
    @IBAction func onClickSample(_ sender: Any) {

        let vc = UIViewController.getViewController(viewControllerEnum: .sampleMain)
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func onClickDebugLeft(_ sender: Any) {
        switch debugStep {
        case .none:
            debugStep = .step1
        default:
            debugStep = .none
        }
    }
    
    @IBAction func onClickDebugCenter(_ sender: Any) {
        switch debugStep {
        case .step1:
            debugStep = .step2
        default:
            debugStep = .none
        }
    }
    
    @IBAction func onClickDebugRight(_ sender: Any) {
        switch debugStep {
        case .step2:
            debugStep = .none
            HHLog.d(.login, "DEBUG MODE")
            let vc = UIViewController.getViewController(viewControllerEnum: .debug)
            present(vc, animated: true)
        default:
            debugStep = .none
        }
    }
}


// MARK: - Delegate
extension LogInViewController: NaverThirdPartyLoginConnectionDelegate {
    func oauth20ConnectionDidFinishRequestACTokenWithAuthCode() {
        
        guard let accessToken = NaverThirdPartyLoginConnection.getSharedInstance().accessToken else {
            HHLog.e(.login, .no0052, "accessToken is nil")
            return
        }
        HHLog.d(.login, "accessToken = \(accessToken)")
        naverRepository?.getUserInfo(accessToken: accessToken)
            .subscribe(onSuccess: { result in
                HHLog.d(.network, "result = \(result)")
                AccountManager.shared.loginType = LoginTypeEnum.naver.rawValue
                AccountManager.shared.accessToken3rd = NaverThirdPartyLoginConnection.getSharedInstance().accessToken
                AccountManager.shared.refreshToken3rd = NaverThirdPartyLoginConnection.getSharedInstance().refreshToken
                AccountManager.shared.userEmail = result.email
                AccountManager.shared.userName = result.name
                AccountManager.shared.processLogin()
            }, onFailure: { error in
                HHLog.e(.login, .no0053, "naver login fail")
                HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO(""), contentsText: TODO("네이버 로그인에 실패했습니다."), okText: TODO("확인"))
            })
            .disposed(by: disposeBag)
    }
    
    func oauth20ConnectionDidFinishRequestACTokenWithRefreshToken() {
        HHLog.t(.login)
    }
    
    func oauth20ConnectionDidFinishDeleteToken() {
        HHLog.t(.login)
    }
    
    func oauth20Connection(_ oauthConnection: NaverThirdPartyLoginConnection!, didFailWithError error: Error!) {
        HHLog.e(.login, .no0054, " error = \(error.localizedDescription)")
    }
}

extension LogInViewController: ASAuthorizationControllerDelegate {
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        HHLog.t(.login)
        if let credetial = authorization.credential as? ASAuthorizationAppleIDCredential {
            let user = credetial.user
            HHLog.d(.login, "user = \(user)")
            if let email = credetial.email {
                HHLog.d(.login, "email = \(email)")
                AccountManager.shared.userEmail = email
            } else {
                AccountManager.shared.userEmail = TODO("이메일 없음")
            }
            AccountManager.shared.loginType = LoginTypeEnum.apple.rawValue
            if let nickname = credetial.fullName?.nickname {
                AccountManager.shared.userName = nickname
            } else {
                AccountManager.shared.userName = TODO("이름 없음")
            }
            if let identityToken = credetial.identityToken {
                AccountManager.shared.accessToken3rd = String(decoding: identityToken, as: UTF8.self)
            }
            AccountManager.shared.processLogin()
        }
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        HHLog.e(.login, .no0055, "error = \(error)")
    }
}
