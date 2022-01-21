//
//  PersonInfoEntity.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/31.
//

import Foundation

//{
//"resultcode":"00",
//"message":"success",
//"response":{
//"id":"36685083",
//"enc_id":"ebea31b92288e9e79ad416beab5039c551ccf9cc5052bcb87b10392570543a38",
//"profile_image":"https:\/\/ssl.pstatic.net\/static\/pwe\/address\/img_profile.png",
//"age":"0-9",
//"gender":"U",
//"nickname":"\uc2a4\uc704\ub9ac\ud30c\uc774",
//"email":"kihoon74@naver.com"
//}
//}
struct NaverPersonInfoVO: Codable {
    var resultcode:String?
    var message:String?
    var response:NaverPersonInfoResponseVO?
}

struct NaverPersonInfoResponseVO: Codable {
    var id:String?
    var name:String?
    var email:String?
}


//{
//"id":2055854401,
//"connected_at":"2022-01-03T06:52:39Z",
//"properties":{
//"nickname":"kihoon kim"
//},
//"kakao_account":{
//"profile_nickname_needs_agreement":false,
//"profile":{
//"nickname":"kihoon kim"
//},
//"has_email":true,
//"email_needs_agreement":false,
//"is_email_valid":true,
//"is_email_verified":true,
//"email":"ki_hoon@hotmail.com"
//}
//}
struct KakaoPersonInfoVO: Codable {
    var resultcode:String?
    var message:String?
    var kakao_account:KakaoPersonInfoKakaoAccountVO?
}

struct KakaoPersonInfoKakaoAccountVO: Codable {
    var profile:KakaoPersonInfoKakaoAccountProfileVO?
    var email:String?
}
struct KakaoPersonInfoKakaoAccountProfileVO: Codable {
    var nickname:String?
}
