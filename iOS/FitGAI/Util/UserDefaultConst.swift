//
//  UserDefaultConst.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/07.
//

import Foundation

public enum UserDefaultConst: String {
    case isUsingFrontCamera
    case loginType
    
    // kakao
    //tokenType: "bearer",
    //accessToken: "-2rhs112VFAg1KaSUPmrAfP0-_VlbI91MCIxfAorDNQAAAF-BWk8tA",
    //expiresIn: 43199.0,
    //expiredAt: 2021-12-29 20:58:06 +0000,
    //refreshToken: "AkEVasAdQJD_vyFOrULGNqsIra4cSxLP2j5z3QorDNQAAAF-BWk8sg",
    //refreshTokenExpiresIn: 5183999.0,
    //refreshTokenExpiredAt: 2022-02-27 08:58:06 +0000,

    case idTokenGoogle    // Google
    case accessToken3rd
    case refreshToken3rd
    case userEmail
    case userName

    case id
    case accessToken

    case version
    case buildNo
    case logEnable
    case allocWatch

    
    static func registerUserDefault() {
        var defaultValue:[String:Any] = [String:Any]()
        defaultValue[UserDefaultConst.isUsingFrontCamera.rawValue] = false
#if DEBUG
        defaultValue[UserDefaultConst.logEnable.rawValue] = true
        defaultValue[UserDefaultConst.allocWatch.rawValue] = true
#else
        defaultValue[UserDefaultConst.logEnable.rawValue] = false
        defaultValue[UserDefaultConst.allocWatch.rawValue] = false
#endif
        UserDefaults.standard.register(defaults: defaultValue)
    }

    // MARK: - Public Method
    static func printUserDefault() {
        HHLog.d(.core,  "-------------------------------------------------------------------------------------------------------------------")
        HHLog.d(.core,  "isUsingFrontCamera = \(UserDefaults.standard.value(forKey: UserDefaultConst.isUsingFrontCamera.rawValue))")
        HHLog.d(.core,  "loginType = \(UserDefaults.standard.value(forKey: UserDefaultConst.loginType.rawValue))")
        HHLog.d(.core,  "idTokenGoogle = \(UserDefaults.standard.value(forKey: UserDefaultConst.idTokenGoogle.rawValue))")
        HHLog.d(.core,  "accessToken3rd = \(UserDefaults.standard.value(forKey: UserDefaultConst.accessToken3rd.rawValue))")
        HHLog.d(.core,  "refreshToken3rd = \(UserDefaults.standard.value(forKey: UserDefaultConst.refreshToken3rd.rawValue))")
        HHLog.d(.core,  "userEmail = \(UserDefaults.standard.value(forKey: UserDefaultConst.userEmail.rawValue))")
        HHLog.d(.core,  "userName = \(UserDefaults.standard.value(forKey: UserDefaultConst.userName.rawValue))")
        HHLog.d(.core,  "id = \(UserDefaults.standard.value(forKey: UserDefaultConst.id.rawValue))")
        HHLog.d(.core,  "accessToken = \(UserDefaults.standard.value(forKey: UserDefaultConst.accessToken.rawValue))")

        HHLog.d(.core,  "version = \(UserDefaults.standard.value(forKey: UserDefaultConst.version.rawValue))")
        HHLog.d(.core,  "buildNo = \(UserDefaults.standard.value(forKey: UserDefaultConst.buildNo.rawValue))")
        HHLog.d(.core,  "logEnable = \(UserDefaults.standard.value(forKey: UserDefaultConst.logEnable.rawValue))")
        HHLog.d(.core,  "allocWatch = \(UserDefaults.standard.value(forKey: UserDefaultConst.allocWatch.rawValue))")
        HHLog.d(.core,  "-------------------------------------------------------------------------------------------------------------------")
    }
}

