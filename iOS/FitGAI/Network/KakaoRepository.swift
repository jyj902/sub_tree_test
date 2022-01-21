//
//  KakaoRepository.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/03.
//

import Foundation
import RxSwift

class KakaoRepository {
    // MARK: - Private Variable
    private var disposeBag =  DisposeBag()
    private var networking:NetworkingProtocol
    
    // MARK: - Override Method or Basic Method
    init(requestNetworkSubject:PublishSubject<Void>?) {
        networking = Networking(style: HHStyle(NetworkingStyle.verboseLogger | NetworkingStyle.activityIndicator), requestNetworkSubject:requestNetworkSubject)
    }
    
    // MARK: - Public Method
    func getUserInfo(accessToken:String) -> Single<PersonModel> {
        return networking.request(KakaoAPI.getUserInfo(accessToken: accessToken))
            .map(KakaoPersonInfoVO.self)
            .map { result -> PersonModel in
                var personModel = PersonModel(name: result.kakao_account?.profile?.nickname ?? TODO("이름 정보 없음"), email: result.kakao_account?.email ?? TODO("이메일 정보 없음"))
                return personModel
            }
    }
}
