//
//  HealthRepository.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import Foundation
import RxSwift

class HealthRepository {
    // MARK: - Private Variable
    private var disposeBag =  DisposeBag()
    private var networking:NetworkingProtocol
    
    // MARK: - Override Method or Basic Method
    init(requestNetworkSubject:PublishSubject<Void>?) {
        networking = Networking(style: HHStyle(NetworkingStyle.verboseLogger | NetworkingStyle.activityIndicator | NetworkingStyle.appExitOnError), requestNetworkSubject:requestNetworkSubject)
    }
    
    // MARK: - Public Method
    func requestLogin(loginType:String, accessToken:String) -> Single<LoginInfoModel> {
        return networking.request(HealthAPI.login(loginType: loginType, accessToken: accessToken))
            .map{ result in
                HHLog.d(.network, "result = \(result)")
                return result
            }
            .map(LoginInfoVO.self)
            .map { result -> LoginInfoModel in
                return LoginInfoModel(loginInfoVO: result)
            }
    }
    
    func requestBodyPartCourseList() -> Single<[BodyPartCourseModel]> {
        return networking.request(HealthAPI.fetchBodyPartCourseList)
            .map{ result in
                HHLog.d(.network, "result = \(result)")
                return result
            }
            .map([BodyPartCourseVO].self)
            .map { result -> [BodyPartCourseModel] in
                var bodyPartCourses:[BodyPartCourseModel] = []
                for item in result {
                    let course = BodyPartCourseModel(bodyPartCourseVO: item)
                    bodyPartCourses.append(course)
                }
                return bodyPartCourses
            }
    }
    
    func requestExerciseContentList(id:Int) -> Single<[ExerciseContentModel]> {
        return networking.request(HealthAPI.fetchExerciseContentList(id: id))
            .map{ result in
                HHLog.d(.network, "result = \(result)")
                return result
            }
            .map([ExerciseContentVO].self)
            .map { result -> [ExerciseContentModel] in
                var exerciseContents:[ExerciseContentModel] = []
                for item in result {
                    let content = ExerciseContentModel(exerciseContentVO: item)
                    exerciseContents.append(content)
                }
                return exerciseContents
            }
    }
}
