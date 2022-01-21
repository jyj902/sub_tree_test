//
//  File.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/25.
//

import Foundation
import UIKit

class DebugVariable {
    // MARK: - Public Variable

    public static let shared = DebugVariable()
    var objcAllocatedClasses:[String]
    var viewAllocCountDic:[String:Int]
    var logEnable:Bool
    var allocWatch:Bool

    // MARK: - Override Method or Basic Method

    private init() {
        objcAllocatedClasses = []
        viewAllocCountDic = [:]
        logEnable = false
        allocWatch = false
    }
    
    // MARK: - Public Method
    func addAllocCount(_ viewController: UIViewController) {
        let callerName = String(describing:type(of:viewController.self))
        if let count = viewAllocCountDic[callerName] {
            viewAllocCountDic[callerName] = count + 1
        } else {
            viewAllocCountDic[callerName] = 1
        }

        NotificationCenter.default.post(name:Notification.Name(NotificationConst.debugNotificationAllocUpdated.rawValue) , object: nil, userInfo: nil)
    }
    
    func subtractAllocCount(_ viewController: UIViewController) {
        let callerName = String(describing:type(of:viewController.self))
        guard var count = viewAllocCountDic[callerName] else {
            return
        }
        count -= 1
        if count == 0 {
            viewAllocCountDic.removeValue(forKey: callerName)
        } else {
            viewAllocCountDic[callerName] = count
        }
        NotificationCenter.default.post(name:Notification.Name(NotificationConst.debugNotificationAllocUpdated.rawValue) , object: nil, userInfo: nil)
    }
}
