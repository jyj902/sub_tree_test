//
//  PoseClassifier.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation
import MLKit

private struct Distance: Comparable{
    var poseSample:PoseSample
    var distance:Float
    
    static func == (lhs: Distance, rhs: Distance) -> Bool {
        return lhs.poseSample as! AnyHashable == rhs.poseSample as! AnyHashable &&
            lhs.distance == rhs.distance
    }

    static func < (lhs: Distance, rhs: Distance) -> Bool {
        return lhs.distance < rhs.distance
    }
}

class PoseClassifier: NSObject{
    private static let MAX_DISTANCE_TOP_K = 30
    private static let MEAN_DISTANCE_TOP_K = 10
    
    // Note Z has a lower weight as it is generally less accurate than X & Y.
    private static let AXES_WEIGHTS = PointF3D(x:1, y:1, z:0.5)
    private class func extractPoseLandmarks(pose: IOSPose.PoseLandmark) -> [PointF3D] {
        var landmarks: [PointF3D] = [PointF3D]()
//        for poseLandmark in pose.landmarks {
//            landmarks.append(PointF3D(x:poseLandmark.position.x, y:poseLandmark.position.y, z:poseLandmark.position.z))
//        }
        landmarks = sortLandmark(poseData: pose)
//        ExerciseGlobal.shared.poseCsvData.append(landmarks)
        return landmarks
    }
    
    private let poseSamples: [PoseSample]
    private let type: Int
    private let maxDistanceTopK: Int
    private let meanDistanceTopK: Int
    private let axesWeights: PointF3D
    
    init(poseSamples: [PoseSample], type: Int, maxDistanceTopK: Int = PoseClassifier.MAX_DISTANCE_TOP_K, meanDistanceTopK: Int = PoseClassifier.MEAN_DISTANCE_TOP_K, axesWeights: PointF3D = PoseClassifier.AXES_WEIGHTS) {
        self.poseSamples = poseSamples
        self.type = type
        self.maxDistanceTopK = maxDistanceTopK
        self.meanDistanceTopK = meanDistanceTopK
        self.axesWeights = axesWeights
        super.init()
    }
    
    
    func confidenceRange() -> Int {
        return min(maxDistanceTopK, meanDistanceTopK)
    }

    func classify(pose: IOSPose.PoseLandmark) -> ClassificationResult {
        return classify(landmarks:PoseClassifier.extractPoseLandmarks(pose:pose))
    }

    
    func classify(landmarks: [PointF3D]) -> ClassificationResult {
        let result = ClassificationResult()
        // Return early if no landmarks detected.
        if (landmarks.count == 0) {
            return result
        }

        // We do flipping on X-axis so we are horizontal (mirror) invariant.
        var flippedLandmarks: [PointF3D] = [PointF3D](landmarks)
        let poseembedding = PoseEmbedding()
        flippedLandmarks = Utils.multiplyAll(flippedLandmarks, PointF3D(x:-1, y:1, z:1))
        let embedding: [PointF3D] = poseembedding.getPoseEmbedding(landmarks:landmarks, type:type)
        let flippedEmbedding: [PointF3D] =
        poseembedding.getPoseEmbedding(landmarks:flippedLandmarks, type:type)


        // Classification is done in two stages:
        //  * First we pick top-K samples by MAX distance. It allows to remove samples that are almost
        //    the same as given pose, but maybe has few joints bent in the other direction.
        //  * Then we pick top-K samples by MEAN distance. After outliers are removed, we pick samples
        //    that are closest by average.

        // Keeps max distance on top so we can pop it when top_k size is reached.
//        let maxDistances = PriorityQueue(maxDistanceTopK) { o1: (PoseSample, Float?), o2: (PoseSample, Float?) ->
//            Float.compare(o1.1!, o2.1)
//        }
        var maxDistances:[Distance] = [Distance]()
        // Retrieve top K poseSamples by least distance to remove outliers.
        for poseSample in poseSamples {
            let sampleEmbedding: [PointF3D] = poseSample.getEmbedding()
            var originalMax:Float = 0
            var flippedMax:Float = 0
            for i in embedding.indices {
                let subData = Utils.subtract(embedding[i], sampleEmbedding[i])
                let mulData = Utils.multiply(subData, axesWeights)
                let absData = Utils.maxAbs(mulData)
                originalMax = max(originalMax, absData)
                let flippedubData = Utils.subtract(flippedEmbedding[i], sampleEmbedding[i])
                let flippedmulData = Utils.multiply(flippedubData, axesWeights)
                let flippedabsData = Utils.maxAbs(flippedmulData)
                flippedMax = max(flippedMax, flippedabsData)
            }
            // Set the max distance as min of original and flipped max distance.
            maxDistances.append(Distance(poseSample: poseSample, distance: min(originalMax, flippedMax)))
            // We only want to retain top n so pop the highest distance.
        }
        if (maxDistances.count > maxDistanceTopK) {
            //maxDistances.poll()
            maxDistances.sort(by: <)
            maxDistances.removeLast(maxDistances.count - maxDistanceTopK)
        }


        // Keeps higher mean distances on top so we can pop it when top_k size is reached.
//        let meanDistances = PriorityQueue(meanDistanceTopK) { o1: (PoseSample, Float?), o2: (PoseSample, Float?) ->
//            Float.compare(o1.second!, o2.second!)
//        }
        var meanDistances:[Distance] = [Distance]()
        // Retrive top K poseSamples by least mean distance to remove outliers.
        for sampleDistances in maxDistances {
            let poseSample = sampleDistances.poseSample
            let sampleEmbedding: [PointF3D] = poseSample.getEmbedding()
            var originalSum:Float = 0
            var flippedSum:Float = 0
            for i in embedding.indices {
                originalSum += Utils.sumAbs(
                    Utils.multiply(
                        Utils.subtract(embedding[i], sampleEmbedding[i]), axesWeights
                    )
                )
                flippedSum += Utils.sumAbs(
                    Utils.multiply(Utils.subtract(flippedEmbedding[i], sampleEmbedding[i]), axesWeights)
                )
            }
            // Set the mean distance as min of original and flipped mean distances.
            let meanDistance = min(originalSum, flippedSum) / Float(embedding.count * 2)
            meanDistances.append(Distance(poseSample:poseSample, distance :meanDistance))
            
            if (meanDistances.count > meanDistanceTopK) {
                meanDistances.sort(by: < )
                meanDistances.removeLast(meanDistances.count - meanDistanceTopK)
            }
        }


        for sampleDistances in meanDistances {
            let className: String = sampleDistances.poseSample.getClassName()
            result.incrementClassConfidence(className:className)
        }
        return result
    }
}
func sortLandmark(poseData: IOSPose.PoseLandmark) -> [PointF3D]{
    var landmark: [PointF3D] = [PointF3D]()
    //0 nose
    landmark.append(PointF3D(x:poseData.landmark(0).position.x,
                             y:poseData.landmark(0).position.y,
                              z:poseData.landmark(0).position.z))
    //1 leftEyeInner
    landmark.append(PointF3D(x:poseData.landmark(1).position.x,
                             y:poseData.landmark(1).position.y,
                              z:poseData.landmark(1).position.z))
    //2 leftEye
    landmark.append(PointF3D(x:poseData.landmark(2).position.x,
                             y:poseData.landmark(2).position.y,
                              z:poseData.landmark(2).position.z))
    //3 leftEyeOuter
    landmark.append(PointF3D(x:poseData.landmark(3).position.x,
                             y:poseData.landmark(3).position.y,
                              z:poseData.landmark(3).position.z))
    //4 rightEyeInner
    landmark.append(PointF3D(x:poseData.landmark(4).position.x,
                             y:poseData.landmark(4).position.y,
                              z:poseData.landmark(4).position.z))
    //5 rightEye
    landmark.append(PointF3D(x:poseData.landmark(5).position.x,
                             y:poseData.landmark(5).position.y,
                              z:poseData.landmark(5).position.z))
    //6 rightEyeOuter
    landmark.append(PointF3D(x:poseData.landmark(6).position.x,
                             y:poseData.landmark(6).position.y,
                              z:poseData.landmark(6).position.z))
    //7 leftEar
    landmark.append(PointF3D(x:poseData.landmark(7).position.x,
                             y:poseData.landmark(7).position.y,
                              z:poseData.landmark(7).position.z))
    //8 rightEar
    landmark.append(PointF3D(x:poseData.landmark(8).position.x,
                             y:poseData.landmark(8).position.y,
                              z:poseData.landmark(8).position.z))
    //9 mouthLeft
    landmark.append(PointF3D(x:poseData.landmark(9).position.x,
                             y:poseData.landmark(9).position.y,
                              z:poseData.landmark(9).position.z))
    //10 mouthRight
    landmark.append(PointF3D(x:poseData.landmark(10).position.x,
                             y:poseData.landmark(10).position.y,
                              z:poseData.landmark(10).position.z))
    //11 leftShoulder
    landmark.append(PointF3D(x:poseData.landmark(11).position.x,
                             y:poseData.landmark(11).position.y,
                              z:poseData.landmark(11).position.z))
    //12 rightShoulder
    landmark.append(PointF3D(x:poseData.landmark(12).position.x,
                             y:poseData.landmark(12).position.y,
                              z:poseData.landmark(12).position.z))
    //13 leftElbow
    landmark.append(PointF3D(x:poseData.landmark(13).position.x,
                             y:poseData.landmark(13).position.y,
                              z:poseData.landmark(13).position.z))
    //14 rightElbow
    landmark.append(PointF3D(x:poseData.landmark(14).position.x,
                             y:poseData.landmark(14).position.y,
                              z:poseData.landmark(14).position.z))
    //15 leftWrist
    landmark.append(PointF3D(x:poseData.landmark(15).position.x,
                             y:poseData.landmark(15).position.y,
                              z:poseData.landmark(15).position.z))
    //16 rightWrist
    landmark.append(PointF3D(x:poseData.landmark(16).position.x,
                             y:poseData.landmark(16).position.y,
                              z:poseData.landmark(16).position.z))
    //17 leftPinkyFinger
    landmark.append(PointF3D(x:poseData.landmark(17).position.x,
                             y:poseData.landmark(17).position.y,
                              z:poseData.landmark(17).position.z))
    //18 rightPinkyFinger
    landmark.append(PointF3D(x:poseData.landmark(18).position.x,
                             y:poseData.landmark(18).position.y,
                              z:poseData.landmark(18).position.z))
    //19 leftIndexFinger
    landmark.append(PointF3D(x:poseData.landmark(19).position.x,
                             y:poseData.landmark(19).position.y,
                              z:poseData.landmark(19).position.z))
    //20 rightIndexFinger
    landmark.append(PointF3D(x:poseData.landmark(20).position.x,
                             y:poseData.landmark(20).position.y,
                              z:poseData.landmark(20).position.z))
    //21 leftThumb
    landmark.append(PointF3D(x:poseData.landmark(21).position.x,
                             y:poseData.landmark(21).position.y,
                              z:poseData.landmark(21).position.z))
    //22 rightThumb
    landmark.append(PointF3D(x:poseData.landmark(22).position.x,
                             y:poseData.landmark(22).position.y,
                              z:poseData.landmark(22).position.z))
    //23 leftHip
    landmark.append(PointF3D(x:poseData.landmark(23).position.x,
                             y:poseData.landmark(23).position.y,
                              z:poseData.landmark(23).position.z))
    //24 rightHip
    landmark.append(PointF3D(x:poseData.landmark(24).position.x,
                             y:poseData.landmark(24).position.y,
                              z:poseData.landmark(24).position.z))
    //25 leftKnee
    landmark.append(PointF3D(x:poseData.landmark(25).position.x,
                             y:poseData.landmark(25).position.y,
                              z:poseData.landmark(25).position.z))
    //26 rightKnee
    landmark.append(PointF3D(x:poseData.landmark(26).position.x,
                             y:poseData.landmark(26).position.y,
                              z:poseData.landmark(26).position.z))
    //27 leftAnkle
    landmark.append(PointF3D(x:poseData.landmark(27).position.x,
                             y:poseData.landmark(27).position.y,
                              z:poseData.landmark(27).position.z))
    //28 rightAnkle
    landmark.append(PointF3D(x:poseData.landmark(28).position.x,
                             y:poseData.landmark(28).position.y,
                              z:poseData.landmark(28).position.z))
    //29 leftHeel
    landmark.append(PointF3D(x:poseData.landmark(29).position.x,
                             y:poseData.landmark(29).position.y,
                              z:poseData.landmark(29).position.z))
    //30 rightHeel
    landmark.append(PointF3D(x:poseData.landmark(30).position.x,
                             y:poseData.landmark(30).position.y,
                              z:poseData.landmark(30).position.z))
    //31 leftToe
    landmark.append(PointF3D(x:poseData.landmark(31).position.x,
                             y:poseData.landmark(31).position.y,
                              z:poseData.landmark(31).position.z))
    //32 rightToe
    landmark.append(PointF3D(x:poseData.landmark(32).position.x,
                             y:poseData.landmark(32).position.y,
                              z:poseData.landmark(32).position.z))
    return landmark
}
