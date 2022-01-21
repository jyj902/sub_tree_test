//
//  HHLocalization.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import UIKit

// MARK: - Public Method
public func STR(_ key: StringConst) -> String {
    return NSLocalizedString(key.rawValue, tableName:"Common", comment: "")
}

public func TODO(_ strText: String) -> String {
    return strText
}

