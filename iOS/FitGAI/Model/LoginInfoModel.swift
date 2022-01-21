//
//  LoginInfoModel.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/06.
//

import Foundation

// H&H 서버에서 관리하는 계정 관리
struct LoginInfoModel {
    // MARK: - Public Variable
    var id:Int
    var name:String
    var email:String
    var token:String
    var loginType:LoginTypeEnum
    
    // MARK: - Override Method or Basic Method
    init(loginInfoVO:LoginInfoVO) {
        self.id = loginInfoVO.id ?? 0
        self.name = loginInfoVO.name ?? ""
        self.email = loginInfoVO.email ?? ""
        self.token = loginInfoVO.token ?? ""
        let loginTypeString = loginInfoVO.registrationId ?? ""
        switch loginTypeString {
        case LoginTypeEnum.apple.rawValue:
            self.loginType = .apple
        case LoginTypeEnum.google.rawValue:
            self.loginType = .google
        case LoginTypeEnum.kakao.rawValue:
            self.loginType = .kakao
        case LoginTypeEnum.naver.rawValue:
            self.loginType = .naver
        default:
            self.loginType = .none
        
        }
    }
}
