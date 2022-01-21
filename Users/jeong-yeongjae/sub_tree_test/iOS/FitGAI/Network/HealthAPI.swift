//
//  HealthAPI.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import Foundation
import Alamofire
import RxSwift
import Moya

enum HealthAPI {
    case login(loginType:String, accessToken:String)
    case fetchBodyPartCourseList
    case fetchExerciseContentList(id:Int)
}

extension HealthAPI: TargetType {
    var baseURL: URL {
        return URL(string: "\(Configuration.shared.urlHost)")!
    }
    
    var path: String {
        switch self {
        case .login(let loginType, let _):
            return "api/v2/login/user/\(loginType)"
        case .fetchBodyPartCourseList:
            return "api/v1/course/bodypart/all"
        case .fetchExerciseContentList(let id):
            return "api/v1/course/bodypart/contents/\(id)"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .login:
            return .post
        case .fetchBodyPartCourseList:
            return .get
        case .fetchExerciseContentList:
            return .get
        }
    }
    
    var sampleData: Data {
        switch self {
        case .login:
            return Data()
        case .fetchBodyPartCourseList:
            let jsonFilePath = Bundle.main.path(forResource: "getBodyPartCourse", ofType: "json")
            let response = try! String(contentsOfFile: jsonFilePath ?? "", encoding: .utf8)
            return response.data(using: .utf8)!
        case .fetchExerciseContentList:
            return Data()
        default:
            return Data()
        }
    }
    
    var task: Task {
        switch self {
        case .login:
            return .requestParameters(parameters: [:], encoding: URLEncoding.default)
        case .fetchBodyPartCourseList:
            //URLEncoding.queryString : url에 파라미터로 전달
            //URLEncoding.httpBody : http body에 파라미터로 전달
            return .requestParameters(parameters: [:], encoding: URLEncoding.default)
        case .fetchExerciseContentList:
            return .requestParameters(parameters: [:], encoding: URLEncoding.default)
        }
    }
    
    var validationType: Moya.ValidationType {
        return .successAndRedirectCodes
    }
    
    var headers: [String : String]? {
        switch self {
        case .login(let loginType, let accessToken):
            return [
                "Content-type":"application/form-data;charset=utf-8",
                "token":accessToken,
                "registrationId":loginType,
            ]
        default :
            return nil
        }
    }
}
