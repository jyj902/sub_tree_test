//
//  ClassificationResult.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation

class ClassificationResult {
    var classConfidences:[String:Float]
    init() {
        classConfidences = [:]
    }
    
    func getAllClasses() -> Set<String> {
        var set = Set<String>()
        for key in classConfidences.keys {
            set.insert(key)
        }
        return set
    }
    
    func getClassConfidence(className: String) -> Float {
        if classConfidences[className] != nil {
            return classConfidences[className]!
        }  else  {
            return 0.0
        }
     }
    
//    func getMaxConfidenceClass() -> String {
//        let sortClassConfidences = classConfidences.sorted { (left: Dictionary<String, Float>.Element, right:  Dictionary<String, Float>.Element) -> Bool in
//            return left.value < right.value
//        }
//        return sortClassConfidences.last?.key ?? "down"
//    }
    
    func getMaxConfidenceClass() -> String {
        let maxValue = classConfidences.values.max()
        for item in classConfidences {
            if item.value == maxValue {
                return item.key
            }
        }
        return "ERROR"
    }
    
    func incrementClassConfidence(className: String?) {
        if (classConfidences[className!] != nil )  {
            classConfidences[className!] = classConfidences[className!]! + 1
        }
        else{
            classConfidences[className!] = 1
        }
    }
    func putClassConfidence(className: String, confidence: Float) {
        classConfidences[className] = confidence
    }

}
