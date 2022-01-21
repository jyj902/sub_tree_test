//
//  PoseClassifierProcessor.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation
import MLKit

class PoseClassifierProcessor : NSObject {
    static var type: Int? = nil
    static var CompanionExercise: (String, Int)? = nil
    static var poseState = ""
    static var repsAfter = 0
    static var repsBefore = 0
    // Specify classes for which we want rep counting.
    // These are the labels in the given {@code POSE_SAMPLES_FILE}. You can set your own class labels
    // for your pose samples.
    private static let PUSHUPS_CLASS = "down"
    private static let SQUATS_CLASS = ""
    private static let POSE_CLASSES = [//            PUSHUPS_CLASS, SQUATS_CLASS
        PUSHUPS_CLASS
    ]
    
    private let isStreamMode:Bool
    private var emaSmoothing:EMASmoothing?
    private var repCounters:[RepetitionCounter]?
    private var poseClassifier:PoseClassifier?
    private var lastRepResult:String?

    init(_ isStreamMode: Bool, _ Exercise: (String, Int)) {
        self.isStreamMode = isStreamMode
        super.init()
        if isStreamMode {
            emaSmoothing = EMASmoothing()
            repCounters = [RepetitionCounter]()
            lastRepResult = ""
        }
        PoseClassifierProcessor.type = Exercise.1
        PoseClassifierProcessor.CompanionExercise = Exercise
        loadPoseSamples(Exercise: Exercise)
    }
    
    
    private func loadPoseSamples(Exercise: (String, Int)) {
        var poseSamples: [PoseSample] = [PoseSample]()
        
        let exerciseFile = Exercise.0

        var dataString: String? = nil
        do {
            dataString = try String(contentsOfFile: Bundle.main.path(forResource: exerciseFile, ofType: "csv")!, encoding: .utf8)
        } catch {
            HHLog.e(.poseDetect, .no0001,"exerciseFile = \(exerciseFile)")
        }
        
        //let csvData = dataString?.data(using: .utf8)
        let rowData:[String] = dataString!.components(separatedBy: "\n")
        for data in rowData{
            let poseSample = PoseSample.getPoseSample(csvLine:data, separator:",")
            if let poseSample = poseSample {
                poseSamples.append(poseSample)
            }
        }
        poseClassifier = PoseClassifier(poseSamples:poseSamples, type:Exercise.1)
        if (isStreamMode) {
            for className in PoseClassifierProcessor.POSE_CLASSES {
                repCounters!.append(RepetitionCounter(className:className))
            }
        }
    }
    
    func getPoseResult(pose: IOSPose.PoseLandmark) -> [String?] {
        //Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper())
        var result: [String?] = [String?]()
        var classification = poseClassifier!.classify(pose:pose)

        // Update {@link RepetitionCounter}s if {@code isStreamMode}.
        if (isStreamMode) {
            // Feed pose to smoothing even if no pose found.
            classification = emaSmoothing!.getSmoothedResult(classificationResult: classification)

            // Return early without updating repCounter if no pose found.
            if (pose.landmark == nil) {
                result.append(lastRepResult)
                return result
            }
            for repCounter in repCounters! {
                PoseClassifierProcessor.repsBefore = repCounter.numRepeats
                PoseClassifierProcessor.repsAfter = repCounter.addClassificationResult(classificationResult:classification)
                if (PoseClassifierProcessor.repsAfter > PoseClassifierProcessor.repsBefore) {
                    // Play a fun beep when rep counter updates.
//                    val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
//                    tg.startTone(ToneGenerator.TONE_PROP_BEEP)
                    lastRepResult = "\(repCounter.className) : \(PoseClassifierProcessor.repsAfter) reps"
                    break
                }
            }
//            result.append(lastRepResult)
        }

        // Add maxConfidence class of current frame to result if pose is found.
        if (pose.landmark != nil) {
            let maxConfidenceClass: String = classification.getMaxConfidenceClass()
            PoseClassifierProcessor.poseState = maxConfidenceClass
            let confidenceData =
            round(classification.getClassConfidence(className:maxConfidenceClass) / Float(poseClassifier!.confidenceRange()) * 1000)/1000
            let maxConfidenceClassResult = "\(PoseClassifierProcessor.repsAfter) reps \(maxConfidenceClass) : \(confidenceData) con"
            result.append(maxConfidenceClassResult)
        }
        return result
    }
     
}
