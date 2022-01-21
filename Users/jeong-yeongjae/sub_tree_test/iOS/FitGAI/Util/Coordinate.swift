//
//  Coordinate.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/11/11.
//

import UIKit

class Coordinate {
    static var safeInsetTop: CGFloat {
        if let keyWindow = UIApplication.shared.keyWindow {
            return keyWindow.safeAreaInsets.top
        }
        return 0
    }
    
    static var safeInsetBottom: CGFloat {
        if let keyWindow = UIApplication.shared.keyWindow {
            return keyWindow.safeAreaInsets.bottom
        }
        return 0
    }
    
    static var safeInsetLeft: CGFloat {
        if let keyWindow = UIApplication.shared.keyWindow {
            return keyWindow.safeAreaInsets.left
        }
        return 0
    }
    
    static var safeInsetRight: CGFloat {
        if let keyWindow = UIApplication.shared.keyWindow {
            return keyWindow.safeAreaInsets.right
        }
        return 0
    }
    
    static var height: CGFloat {
        return UIScreen.main.bounds.height
    }

    static var width: CGFloat {
        return UIScreen.main.bounds.width
    }
}
