//
//  LoginInfoEntity.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/06.
//

import Foundation

struct LoginInfoVO: Codable {
    var id:Int?
    var name:String?
    var email:String?
    var token:String?
    var createDate:String?
    var registrationId:String?
    var activate:Bool?
}
