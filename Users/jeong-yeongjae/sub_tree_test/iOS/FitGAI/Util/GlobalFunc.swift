//
//  File.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/23.
//

import Foundation
import UIKit

public func nonNilString(_ object:Any?) -> Any {
    return object ?? ""
}

public func delayExecute(_ seconds: Double, completion:@escaping ()->()) {
    let popTime = DispatchTime.now() + Double(Int64( Double(NSEC_PER_SEC) * seconds )) / Double(NSEC_PER_SEC)
    
    DispatchQueue.main.asyncAfter(deadline: popTime) {
        completion()
    }
}
