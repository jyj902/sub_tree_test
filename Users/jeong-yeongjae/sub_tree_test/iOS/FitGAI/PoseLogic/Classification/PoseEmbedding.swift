//
//  PoseEmbedding.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation
import MLKit

class PoseEmbedding {
    private static let TORSO_MULTIPLIER:Float = 2.5
    func getPoseEmbedding(landmarks: [PointF3D], type: Int) -> [PointF3D] {
        let normalizedLandmarks = normalize(landmarks: landmarks)
        return getEmbedding(lm:normalizedLandmarks, type:type)
    }
    
    private func normalize(landmarks: [PointF3D]) -> [PointF3D] {
        var normalizedLandmarks: [PointF3D] = landmarks
        // Normalize translation.
        let center: PointF3D = Utils.average(landmarks[PoseLandmark.LEFT_HIP], landmarks[PoseLandmark.RIGHT_HIP])
        normalizedLandmarks = Utils.subtractAll(normalizedLandmarks, center)

        // Normalize scale.
        normalizedLandmarks = Utils.multiplyAll(normalizedLandmarks, (1 / getPoseSize(landmarks: normalizedLandmarks)))
        // Multiplication by 100 is not required, but makes it easier to debug.
        normalizedLandmarks = Utils.multiplyAll(normalizedLandmarks, 100)
        return normalizedLandmarks
    }
    
    
    // Translation normalization should've been done prior to calling this method.
    private func getPoseSize(landmarks: [PointF3D]) -> Float {
        // Note: This approach uses only 2D landmarks to compute pose size as using Z wasn't helpful
        // in our experimentation but you're welcome to tweak.
        let hipsCenter: PointF3D = Utils.average(landmarks[PoseLandmark.LEFT_HIP], landmarks[PoseLandmark.RIGHT_HIP])
        let shouldersCenter: PointF3D = Utils.average(landmarks[PoseLandmark.LEFT_SHOULDER], landmarks[PoseLandmark.RIGHT_SHOULDER])
        let torsoSize: Float = Utils.l2Norm2D(Utils.subtract(hipsCenter, shouldersCenter))
        var maxDistance = torsoSize * PoseEmbedding.TORSO_MULTIPLIER
        // torsoSize * TORSO_MULTIPLIER is the floor we want based on experimentation but actual size
        // can be bigger for a given pose depending on extension of limbs etc so we calculate that.
        for landmark in landmarks {
            let distance: Float = Utils.l2Norm2D(Utils.subtract(hipsCenter, landmark))
            if (distance > maxDistance) {
                maxDistance = distance
            }
        }
        return maxDistance
    }

    private func getEmbedding(lm: [PointF3D], type: Int) -> [PointF3D] {
        var embedding: [PointF3D] = [PointF3D]()
        
        // We use several pairwise 3D distances to form pose embedding. These were selected
        // based on experimentation for best results with our default pose classes as captued in the
        // pose samples csv. Feel free to play with this and add or remove for your use-cases.
        
        // We group our distances by number of joints between the pairs.
        // One joint.
        switch (type){
        case EXERCISE_INFORMATION.FULL :
            embedding.append(
                Utils.subtract(
                    Utils.average(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.RIGHT_HIP]),
                    Utils.average(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_SHOULDER])
                )
            )
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ELBOW]
                )
            )
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ELBOW]
                )
            )
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_ANKLE]))
            
            // Two joints.
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]
                )
            )
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]
                )
            )
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ANKLE]))
            
            // Four joints.
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))
            
            // Five joints.
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ANKLE]
                )
            )
            embedding.append(
                Utils.subtract(
                    lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ANKLE]
                )
            )
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))
            
            // Cross body.
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.RIGHT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ANKLE], lm[PoseLandmark.RIGHT_ANKLE]))
            return embedding
            
        case EXERCISE_INFORMATION.LOWER :
            //embedding 개수 증가로 딜레이 증가  --> 현재 주석으로 감소
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_FOOT_INDEX]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_HEEL]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_HEEL]))
            
            //어깨 하체 지점들 추가
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ANKLE]))
            //                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_FOOT_INDEX]))
            //                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
            //                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_HEEL]))
            //                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_HEEL]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_KNEE]))
            //허리와 각하체 지점들 추가
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ANKLE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_FOOT_INDEX]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_HEEL]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_HEEL]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_KNEE]))
            // Cross body.
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.RIGHT_KNEE]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ANKLE], lm[PoseLandmark.RIGHT_ANKLE]))
            return embedding
            
        case EXERCISE_INFORMATION.UPPER :
            embedding.append(
                Utils.subtract(
                    Utils.average(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.RIGHT_HIP]),
                    Utils.average(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_SHOULDER])
                )
            )
            //shoulder
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]))
            //elbow
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
            //hip
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ELBOW]))
            
            // Cross body.
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_PINKY], lm[PoseLandmark.RIGHT_PINKY]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))
            
            // Add Upper Weight.
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_PINKY], lm[PoseLandmark.RIGHT_PINKY]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_ELBOW]))
            embedding.append(Utils.subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]))
            embedding.append(Utils.subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]))
            return embedding
        default:
            return embedding
        }
    }
}
