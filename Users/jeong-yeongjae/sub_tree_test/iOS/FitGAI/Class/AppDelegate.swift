//
//  AppDelegate.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/23.
//
import UIKit
import Firebase
import GoogleSignIn
import UserNotifications
import FirebaseAnalytics
import FirebaseCrashlytics
import KakaoSDKCommon
import KakaoSDKAuth
import NaverThirdPartyLogin
import RealmSwift

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    // MARK: - Public Variable
    var window: UIWindow?
    
    // MARK: - Override Method or Basic Method
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        GlobalVariable.shared.startTime = TimeInterval(CACurrentMediaTime())
        HHLog.d(.time, "AppDelegate Start : \(CACurrentMediaTime() - GlobalVariable.shared.startTime)")
        
        initializeUserDefault()
        initializeBasicScene()
        initializeMigration()
        initializeFirebase()
        initialize3rdPartyLogin()
        
        UNUserNotificationCenter.current().requestAuthorization(options:[.badge, .alert, .sound]){ (granted, error) in }
        application.registerForRemoteNotifications()

        EXERCISE_INFORMATION.initializeVariables()
        ImageUtilities.doBadSwizzleStuff()
        RealmManager.shared.configurationDefaultRealm(loginType:LoginTypeEnum(rawValue: AccountManager.shared.loginType ?? LoginTypeEnum.none.rawValue) ?? LoginTypeEnum.none, userNo:AccountManager.shared.userEmail)
        
        HHLog.d(.time, "AppDelegate Finished : \(CACurrentMediaTime() - GlobalVariable.shared.startTime)")

        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        HHLog.d(.appDelegate, "url = \(url)")
        
        // Naver 로그인
        if url.scheme == "com.hnh.health" {
            return NaverThirdPartyLoginConnection.getSharedInstance().application(app, open: url, options: options)
        }
        
        //        if (AuthController.isKakaoTalkLoginUrl(url)) {
        if AuthController.handleOpenUrl(url: url, options: options) {
            return true
        }
        //        }
        
        return GIDSignIn.sharedInstance.handle(url)
    }
        
    // Called when APNs has assigned the device a unique token
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        // Convert token to string
        let deviceTokenString = deviceToken.reduce("", {$0 + String(format: "%02X", $1)})
        
        // Print it to console
        HHLog.d(.appDelegate, "APNs device token: \(deviceTokenString)")
        
        // Persist it in your backend in case it's new
    }
    
    // Called when APNs failed to register the device for push notifications
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        // Print the error to console (you should alert the user that registration failed)
        HHLog.d(.appDelegate, "APNs registration failed: \(error)")
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    // Push notification received
    func application(_ application: UIApplication, didReceiveRemoteNotification data: [AnyHashable : Any]) {
        // Print notification payload data
        HHLog.d(.appDelegate, "Push notification received: \(data)")
    }
    
    // MARK: - Public Method
    func finishApp() {
        let app = UIApplication.shared
        app.perform(Selector("suspend"))

        delayExecute(1) {
            exit(EXIT_SUCCESS)
        }
    }
    
    // MARK: - Private Method
    func initializeUserDefault() {
        UserDefaultConst.registerUserDefault()

        if let logEnable = UserDefaults.standard.value(forKey: UserDefaultConst.logEnable.rawValue) as? Bool {
            DebugVariable.shared.logEnable = logEnable
        }
        if let allocWatch = UserDefaults.standard.value(forKey: UserDefaultConst.allocWatch.rawValue) as? Bool {
            DebugVariable.shared.allocWatch = allocWatch
        }
        UserDefaultConst.printUserDefault()
    }
    
    func initializeBasicScene() {
        let viewController = SplashViewController(nibName: nil, bundle: nil)
        let screen = UIScreen.main
        let bounds = screen.bounds

        self.window = UIWindow(frame: bounds)
        if let window = window {
            window.backgroundColor = UIColor.bgLight
            window.rootViewController = viewController
            window.makeKeyAndVisible()
        }
    }
    
    func initializeMigration() {
        let infoDic = Bundle.main.infoDictionary
        let version = infoDic?["CFBundleShortVersionString"] as? String
        let buildNo = Int(infoDic?["CFBundleVersion"] as? String ?? "0")

        if let version = version, let buildNo = buildNo {
            if version != GlobalVariable.shared.version ||
                buildNo != GlobalVariable.shared.buildNo {
                // 버젼이 다른 경우 마이그레이션을 한다.
                if GlobalVariable.shared.version == nil {
                    // nothing to do
                } else if version != GlobalVariable.shared.version {
                    // version up 한다.
                    
                }
                
                if FeatureConst.FEATURE_REMOVE_DB_ON_VERSION_UP {
                    try? RealmManager.shared.deleteRealm(loginType:LoginTypeEnum(rawValue:AccountManager.shared.loginType ?? LoginTypeEnum.none.rawValue) ?? LoginTypeEnum.none, userNo: AccountManager.shared.userEmail)
                }
                
                GlobalVariable.shared.version = version
                GlobalVariable.shared.buildNo = buildNo
            }
        }
    }
    
    func initializeFirebase() {
        // firebase
        FirebaseApp.configure()
        
        if let userEmail = AccountManager.shared.userEmail,
           let loginType = AccountManager.shared.loginType,
           let userName = AccountManager.shared.userName {
            Analytics.setUserID(userEmail)
            Crashlytics.crashlytics().setUserID(userEmail)
            Crashlytics.crashlytics().setCustomValue("\(loginType)", forKey: "loginType")
            Crashlytics.crashlytics().setCustomValue("\(userName)", forKey: "userName")
        }
    }

    func initialize3rdPartyLogin() {
        // 구글 로그인
        // OAuth 2.0 클라이언트 ID
        //GIDSignIn.sharedInstance.clientID = "80340447144-u37i296m86os21vkdk6inh363bv2lk9a.apps.googleusercontent.com"

        // 카카오 로그인
        KakaoSDK.initSDK(appKey: "d9327e3f8125041f549b359a6cd6c5ea")
        
        // 네이버 로그인
        let thirdConn = NaverThirdPartyLoginConnection.getSharedInstance()
        thirdConn?.serviceUrlScheme = "com.hnh.health"
        thirdConn?.consumerKey = "FL_p5aVF6mVpGPzM8A19"
        thirdConn?.consumerSecret = "OjjzYWTQhI"
        thirdConn?.appName = "FitGAI"
        
        //사파리에서 인증하는 방식
        thirdConn?.isInAppOauthEnable = true
        //네이버 앱으로 인증하는 방식
        thirdConn?.isNaverAppOauthEnable = false
        thirdConn?.setOnlyPortraitSupportInIphone(true)
    }
}

