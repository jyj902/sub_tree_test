//
//  EMASmoothing.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation

class EMASmoothing {
    private static let DEFAULT_WINDOW_SIZE = 10
    private static let DEFAULT_ALPHA:Float = 0.2

    let windowSize: Int = EMASmoothing.DEFAULT_WINDOW_SIZE
    let alpha: Float = EMASmoothing.DEFAULT_ALPHA
    private var window: [ClassificationResult]
    

    init() {
        window = [ClassificationResult]()
    }

    func getSmoothedResult(classificationResult: ClassificationResult) -> ClassificationResult {
        // If we are at window size, remove the last (oldest) result.
        if (window.count == windowSize) {
            window.remove(at: windowSize - 1)
        }
        // Insert at the beginning of the window.
        window.insert(classificationResult, at: 0)
        var allClasses = Set<String>()
        
        for result in window {
            for key in result.getAllClasses(){
                allClasses.insert(key)
            }
        }
        
        let smoothedResult = ClassificationResult()
        
        for className in allClasses {
            var factor:Float = 1
            var topSum:Float = 0
            var bottomSum:Float = 0
            for result in window {
                let value:Float = result.getClassConfidence(className:className)
                topSum += factor * value
                bottomSum += factor
                factor = (factor * (1.0 - alpha))
            }
            smoothedResult.putClassConfidence(className:className, confidence:topSum / bottomSum)
        }
        return smoothedResult
    }
}
