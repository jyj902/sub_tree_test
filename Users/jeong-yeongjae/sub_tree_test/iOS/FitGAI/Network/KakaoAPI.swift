//
//  KakaoAPI.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/03.
//

import Foundation
import Alamofire
import RxSwift
import Moya

enum KakaoAPI {
    case getUserInfo(accessToken:String)
}

extension KakaoAPI: TargetType {
    var baseURL: URL {
        return URL(string: "https://kapi.kakao.com")!
        //kapi.kakao.com
        //kauth.kakao.com
    }
    
    var path: String {
        switch self {
        case .getUserInfo:
            return "v2/user/me"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .getUserInfo:
            return .get
        }
    }
    
    var sampleData: Data {
        switch self {
        case .getUserInfo:
            let jsonFilePath = Bundle.main.path(forResource: "getUserInfo_Kakao", ofType: "json")
            let response = try! String(contentsOfFile: jsonFilePath ?? "", encoding: .utf8)
            return response.data(using: .utf8)!
        default:
            return Data()
        }
    }
    
    var task: Task {
        switch self {
        case .getUserInfo:
            return .requestParameters(parameters: [:], encoding: URLEncoding.default)
        }
    }
    
    var validationType: Moya.ValidationType {
        return .successAndRedirectCodes
    }
    
    var headers: [String : String]? {
        switch self {
        case .getUserInfo(let accessToken):
            return [
                "Authorization":"Bearer \(accessToken)",
                "Content-type":"application/x-www-form-urlencoded;charset=utf-8"
            ]
        }
    }
}
