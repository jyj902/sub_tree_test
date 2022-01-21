//
//  FeatureConst.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import Foundation

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum AppLevel {
    case release
    case debug
    case demo
}

class FeatureConst {
#if DEBUG
    //static let FEATURE_APP_LEVEL = AppLevel.demo
    static let FEATURE_APP_LEVEL = AppLevel.debug
#else
    //static let FEATURE_APP_LEVEL = AppLevel.demo
    static let FEATURE_APP_LEVEL = AppLevel.release
#endif
    // 해당 부분은 버젼이 달라지면 무조건 DB를 지우게 구현되어있는데, 출시 할 경우 false로 변경해야 합니다.
    static let FEATURE_REMOVE_DB_ON_VERSION_UP = true
    
    // 현재는 개발 시점이라 true로 설정하고, 출시 할 경우 false로 변경해야 합니다.
    static let FEATURE_DEVELOPER_LOGIN = true

}
