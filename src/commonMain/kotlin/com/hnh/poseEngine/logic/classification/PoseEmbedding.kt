package com.hnh.poseengine.logic.classification


import com.hnh.poseengine.logic.PointF3D
import com.hnh.poseengine.logic.PoseLandmarkPoseEngine
import com.hnh.poseengine.logic.classification.Utils.average
import com.hnh.poseengine.logic.classification.Utils.l2Norm2D
import com.hnh.poseengine.logic.classification.Utils.multiplyAll
import com.hnh.poseengine.logic.classification.Utils.subtract
import com.hnh.poseengine.logic.classification.Utils.subtractAll
import com.hnh.poseengine.logic.EXERCISE_INFORMATION


/**
 * Generates embedding for given list of Pose landmarks.
 */
class PoseEmbedding(){
    // Multiplier to apply to the torso to get minimal body size. Picked this by experimentation.
    private val TORSO_MULTIPLIER = 2.5f
    fun getPoseEmbedding(landmarks: List<PointF3D>, Type: Int): List<PointF3D> {
        val normalizedLandmarks = normalize(landmarks)
        return getEmbedding(normalizedLandmarks, Type)
    }

    private fun normalize(landmarks: List<PointF3D>): List<PointF3D> {
        val normalizedLandmarks: MutableList<PointF3D> = ArrayList(landmarks)
        // Normalize translation.
        val center: PointF3D = average(
            landmarks[PoseLandmarkPoseEngine.LEFT_HIP], landmarks[PoseLandmarkPoseEngine.RIGHT_HIP]
        )
        subtractAll(center, normalizedLandmarks)

        // Normalize scale.
        multiplyAll(normalizedLandmarks, (1 / getPoseSize(normalizedLandmarks)))
        // Multiplication by 100 is not required, but makes it easier to debug.
        multiplyAll(normalizedLandmarks, 100F)
        return normalizedLandmarks
    }

    // Translation normalization should've been done prior to calling this method.
    private fun getPoseSize(landmarks: List<PointF3D>): Float {
        // Note: This approach uses only 2D landmarks to compute pose size as using Z wasn't helpful
        // in our experimentation but you're welcome to tweak.
        val hipsCenter: PointF3D = average(
            landmarks[PoseLandmarkPoseEngine.LEFT_HIP], landmarks[PoseLandmarkPoseEngine.RIGHT_HIP]
        )
        val shouldersCenter: PointF3D = average(
            landmarks[PoseLandmarkPoseEngine.LEFT_SHOULDER],
            landmarks[PoseLandmarkPoseEngine.RIGHT_SHOULDER]
        )
        val torsoSize: Float = l2Norm2D(subtract(hipsCenter, shouldersCenter))
        var maxDistance = torsoSize * TORSO_MULTIPLIER
        // torsoSize * TORSO_MULTIPLIER is the floor we want based on experimentation but actual size
        // can be bigger for a given pose depending on extension of limbs etc so we calculate that.
        for (landmark in landmarks) {
            val distance: Float = l2Norm2D(subtract(hipsCenter, landmark))
            if (distance > maxDistance) {
                maxDistance = distance
            }
        }
        return maxDistance
    }

    private fun getEmbedding(lm: List<PointF3D>, Type: Int): List<PointF3D> {
        val embedding: MutableList<PointF3D> = ArrayList()

        // We use several pairwise 3D distances to form pose embedding. These were selected
        // based on experimentation for best results with our default pose classes as captued in the
        // pose samples csv. Feel free to play with this and add or remove for your use-cases.

        // We group our distances by number of joints between the pairs.
        // One joint.
        when (Type){
            EXERCISE_INFORMATION.FULL -> {
                embedding.add(
                    subtract(
                        average(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_HIP]),
                        average(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER])
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_ELBOW]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]
                    )
                )
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))

                // Two joints.
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_WRIST]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]
                    )
                )
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))

                // Four joints.
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))

                // Five joints.
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]
                    )
                )
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))

                // Cross body.
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_WRIST], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ANKLE], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))
                return embedding
            }
            EXERCISE_INFORMATION.LOWER ->{
                //embedding 개수 증가로 딜레이 증가  --> 현재 주석으로 감소
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.LEFT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.LEFT_HEEL]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_HEEL]))

                //어깨 하체 지점들 추가
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))
//                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_FOOT_INDEX]))
//                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
//                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_HEEL]))
//                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_HEEL]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_KNEE]))
                //허리와 각하체 지점들 추가
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_HEEL]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_HEEL]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_KNEE]))
                // Cross body.
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_KNEE], lm[PoseLandmarkPoseEngine.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ANKLE], lm[PoseLandmarkPoseEngine.RIGHT_ANKLE]))
                return embedding
            }
            EXERCISE_INFORMATION.UPPER ->{
                embedding.add(
                    subtract(
                        average(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_HIP]),
                        average(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER])
                    )
                )
                //shoulder
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                //elbow
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                //hip
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_HIP], lm[PoseLandmarkPoseEngine.LEFT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_HIP], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))

                // Cross body.
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_PINKY], lm[PoseLandmarkPoseEngine.RIGHT_PINKY]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_WRIST], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))

                // Add Upper Weight.
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_PINKY], lm[PoseLandmarkPoseEngine.RIGHT_PINKY]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_WRIST], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_ELBOW], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_WRIST], lm[PoseLandmarkPoseEngine.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.LEFT_SHOULDER], lm[PoseLandmarkPoseEngine.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmarkPoseEngine.RIGHT_SHOULDER], lm[PoseLandmarkPoseEngine.LEFT_WRIST]))
                return embedding
            }
            else -> return embedding
        }

    }
}