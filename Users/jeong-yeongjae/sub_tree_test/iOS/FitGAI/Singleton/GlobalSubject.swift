//
//  GlobalSubject.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import Foundation
import RxSwift

class GlobalSubject : NSObject {
    // MARK: - Public Variable
    public static let shared = GlobalSubject()
    
    var lottieSubject = PublishSubject<LottieEnum>()
    var csvSubject = PublishSubject<(String, Int)>()
    var countSubject = PublishSubject<(Int, Int)>()
    
    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
    }
}
