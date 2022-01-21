//
//  Styleable.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import Foundation

public struct HHStyle {
    private var value:Int
    
    // MARK: - Override Method or Basic Method
    init(_ value:Int) {
        self.value = value
    }
    
    // MARK: - Public Method
    mutating func add(_ addValue:Int) {
        value |= addValue
    }
    mutating func removeStyle(_ removeValue:Int) {
        value &= ~removeValue
    }
    mutating func setStyle(_ value:Int) {
        self.value = value
    }
    
    func isInclude(_ value:Int) -> Bool {
        if self.value & value == 0 {
            return false
        } else {
            return true
        }
    }
}


