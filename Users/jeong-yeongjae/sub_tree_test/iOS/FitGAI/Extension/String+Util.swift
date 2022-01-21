//
//  String+Util.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/06.
//

import Foundation
extension String {
    // MARK: - Public Method
    public static func getPureClassName(_ aClass: AnyClass) -> String {
        if let className = NSStringFromClass(aClass.self).split(separator: ".").last {
            return String(className)
        }
        return ""
    }
}
