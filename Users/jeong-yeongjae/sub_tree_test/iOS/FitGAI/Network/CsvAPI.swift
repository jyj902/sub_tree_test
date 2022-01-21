//
//  CsvAPI.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/07.
//

import Foundation
import Alamofire
import RxSwift
import Moya

enum CsvAPI {
    case uploadCsv(data:Data, fileName:String, userId:Int, setNum:Int, exerciseName:String, fileSize:Int, runningTime:Int, isFreeMode:Bool)
}

extension CsvAPI: TargetType {
    var baseURL: URL {
        return URL(string: "\(Configuration.shared.urlHost)")!
    }
    
    var path: String {
        switch self {
        case .uploadCsv:
            return "api/v1/record/personal/new"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .uploadCsv:
            return .post
        }
    }
    
    var sampleData: Data {
        switch self {
        case .uploadCsv:
            return Data()
        default:
            return Data()
        }
    }
    
    public var task: Task {
        switch self {
            
        case let .uploadCsv(data, fileName, userId, setNum, exerciseName, fileSize, runningTime, isFreeMode):
            let skeletonFile = MultipartFormData(provider: .data(data), name: "skeletonFile", fileName: fileName, mimeType: "text/csv")
            let params:[String:Any] = [
                "userId":userId,
                "setNum":setNum,
                "exerciseName":exerciseName,
                "fileSize":fileSize,
                "runningTime":runningTime,
                "isFreeMode":isFreeMode
            ]
            HHLog.d(.network, "uploadCsv() fileName = \(fileName), params = \(params)")
            let multipartData = [skeletonFile]
            return .uploadCompositeMultipart(multipartData, urlParameters: params)
        }
    }
    
    var validationType: Moya.ValidationType {
        return .successAndRedirectCodes
    }
    
    var headers: [String : String]? {
        switch self {
        default :
            return nil
        }
    }
}
