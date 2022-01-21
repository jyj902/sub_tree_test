//
//  GlobalVariable.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import Foundation

class GlobalVariable {
    // MARK: - Public Variable
    public static let shared = GlobalVariable()
    var startTime:Double
    
    var isUsingFrontCamera: Bool {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.isUsingFrontCamera.rawValue) as? Bool ?? false
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.isUsingFrontCamera.rawValue)
        }
    }
    
    var version: String? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.version.rawValue) as? String
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.version.rawValue)
        }
    }

    var buildNo: Int? {
        get {
            return UserDefaults.standard.value(forKey: UserDefaultConst.buildNo.rawValue) as? Int
        }
        set {
            UserDefaults.standard.setValue(newValue, forKey: UserDefaultConst.buildNo.rawValue)
        }
    }
    
    // MARK: - Override Method or Basic Method
    private init() {
        startTime = 0.0
    }
    

}
