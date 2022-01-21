//
//  NaverRepository.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/31.
//

import Foundation
import RxSwift

class NaverRepository {
    // MARK: - Private Variable
    private var disposeBag =  DisposeBag()
    private var networking:NetworkingProtocol
    
    // MARK: - Override Method or Basic Method
    init(requestNetworkSubject:PublishSubject<Void>?) {
        networking = Networking(style: HHStyle(NetworkingStyle.verboseLogger | NetworkingStyle.activityIndicator), requestNetworkSubject:requestNetworkSubject)
    }
    
    // MARK: - Public Method
    func getUserInfo(accessToken:String) -> Single<PersonModel> {
        return networking.request(NaverAPI.getUserInfo(accessToken: accessToken))
            .map(NaverPersonInfoVO.self)
            .map { result -> PersonModel in
                var personModel = PersonModel(name: result.response?.name ?? TODO("이름 정보 없음"), email: result.response?.email ?? TODO("이메일 정보 없음"))
                return personModel
            }
    }
}
