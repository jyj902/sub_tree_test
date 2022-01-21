//
//  UIImage+GUI.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import UIKit

extension UIImage {
    // MARK: - Public Method
    class func image(fromColor hexString: String?) -> UIImage? {
        let rect = CGRect(x: 0, y: 0, width: 1, height: 1)
        UIGraphicsBeginImageContext(rect.size)
        let context = UIGraphicsGetCurrentContext()
        context?.setFillColor(UIColor(hexString: hexString)?.cgColor ?? UIColor.white.cgColor )
        context?.fill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
}
