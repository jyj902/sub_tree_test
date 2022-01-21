//
//  RepetitionCounter.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation

class RepetitionCounter: NSObject {
    static let DEFAULT_ENTER_THRESHOLD:Float = 6.0
    static let DEFAULT_EXIT_THRESHOLD:Float = 4.0
    let enterThreshold: Float = RepetitionCounter.DEFAULT_ENTER_THRESHOLD
    let exitThreshold: Float = RepetitionCounter.DEFAULT_EXIT_THRESHOLD

    var className:String
    var numRepeats = 0
    var poseEntered = false

    init(className:String) {
        self.className = className
        super.init()
    }
    
    func addClassificationResult(classificationResult:ClassificationResult) -> Int {
        let poseConfident = classificationResult.getClassConfidence(className: className)
        if !poseEntered {
            poseEntered = poseConfident > RepetitionCounter.DEFAULT_ENTER_THRESHOLD
            return numRepeats
        }
        
        if poseConfident < exitThreshold {
            numRepeats += 1
            poseEntered = false
        }
        return numRepeats
    }
    
}
