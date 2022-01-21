//
//  AccountManager.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/22.
//

import UIKit
import RxSwift

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum LoginTypeEnum : String {
    case none
    case google
    case kakao
    case naver
    case apple
    case developer
}

class AccountManager : NSObject {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    public static let shared = AccountManager()
    var loginType: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.loginType.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.loginType.rawValue)
        }
    }
    
    // Google
    var idTokenGoogle: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.idTokenGoogle.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.idTokenGoogle.rawValue)
        }
    }
    
    var accessToken3rd: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.accessToken3rd.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.accessToken3rd.rawValue)
        }
    }
    
    var refreshToken3rd: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.refreshToken3rd.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.refreshToken3rd.rawValue)
        }
    }
    
    var userEmail: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.userEmail.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.userEmail.rawValue)
        }
    }

    var userName: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.userName.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.userName.rawValue)
        }
    }
    
    var id: Int? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.id.rawValue) as? Int
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.id.rawValue)
        }
    }
    var accessToken: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.accessToken.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.accessToken.rawValue)
        }
    }
    // MARK: - Private Variable
    private var healthRepository:HealthRepository?
    private var disposeBag = DisposeBag()

    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
        healthRepository = HealthRepository(requestNetworkSubject: nil)
    }
    
    // MARK: - Public Method
    func isLogin() -> Bool {
        if accessToken == nil {
            return false
        } else {
            return true
        }
    }
    
    func processLogin() {
        guard let loginType = loginType else {
            HHLog.e(.login, .no0002, "loginType is nil")
            return
        }
        let token:String?
        if loginType == LoginTypeEnum.google.rawValue {
            token = idTokenGoogle
        } else {
            token = accessToken3rd
        }
        
        guard let token = token else {
            HHLog.e(.login, .no0003, "token is nil")
            return
        }

        healthRepository?.requestLogin(loginType: loginType, accessToken: token)
            .subscribe(onSuccess: { [weak self] result in
                self?.accessToken = result.token
                self?.id = result.id
                RealmManager.shared.configurationDefaultRealm(loginType:LoginTypeEnum(rawValue:AccountManager.shared.loginType ?? LoginTypeEnum.none.rawValue) ?? LoginTypeEnum.none ,userNo:AccountManager.shared.userEmail ?? TODO("이메일 정보 없음"))
                let vc = UIViewController.getViewController(viewControllerEnum: .main)
                UINavigationController.setRootViewController(viewController: vc, withNavigationController: true)
            }, onError: { error in
                HHLog.e(.login, .no0004, "internal login fail")
                HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO(""), contentsText: TODO("내부 로그인에 실패했습니다."), okText: TODO("확인"))
            })
            .disposed(by: disposeBag)
    }
    
    func processLogout() {
        RealmManager.shared.configurationDefaultRealm(loginType:LoginTypeEnum.none, userNo:nil)
        let vc = UIViewController.getViewController(viewControllerEnum: .login)
        UINavigationController.setRootViewController(viewController: vc, withNavigationController: true)

        loginType = nil
        idTokenGoogle = nil
        accessToken3rd = nil
        refreshToken3rd = nil
        userEmail = nil
        userName = nil
        id = nil
        accessToken = nil
    }
}
