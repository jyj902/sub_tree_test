package com.hnh.presentation.logic.classification


import com.google.mlkit.vision.common.PointF3D
import com.google.mlkit.vision.pose.PoseLandmark
//import com.hnh.presentation.logic.PoseProcess
import com.hnh.presentation.logic.classification.Utils.average
import com.hnh.presentation.logic.classification.Utils.l2Norm2D
import com.hnh.presentation.logic.classification.Utils.multiplyAll
import com.hnh.presentation.logic.classification.Utils.subtract
import com.hnh.presentation.logic.classification.Utils.subtractAll
import com.hnh.presentation.util.EXERCISE_INFORMATION
import java.util.*


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
            landmarks[PoseLandmark.LEFT_HIP], landmarks[PoseLandmark.RIGHT_HIP]
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
            landmarks[PoseLandmark.LEFT_HIP], landmarks[PoseLandmark.RIGHT_HIP]
        )
        val shouldersCenter: PointF3D = average(
            landmarks[PoseLandmark.LEFT_SHOULDER],
            landmarks[PoseLandmark.RIGHT_SHOULDER]
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
                        average(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.RIGHT_HIP]),
                        average(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_SHOULDER])
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ELBOW]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ELBOW]
                    )
                )
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_ANKLE]))

                // Two joints.
                embedding.add(
                    subtract(
                        lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]
                    )
                )
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ANKLE]))

                // Four joints.
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))

                // Five joints.
                embedding.add(
                    subtract(
                        lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ANKLE]
                    )
                )
                embedding.add(
                    subtract(
                        lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ANKLE]
                    )
                )
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))

                // Cross body.
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_ANKLE], lm[PoseLandmark.RIGHT_ANKLE]))
                return embedding
            }
            EXERCISE_INFORMATION.LOWER ->{
                //embedding 개수 증가로 딜레이 증가  --> 현재 주석으로 감소
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.LEFT_HEEL]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_KNEE], lm[PoseLandmark.RIGHT_HEEL]))

                //어깨 하체 지점들 추가
                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ANKLE]))
//                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_FOOT_INDEX]))
//                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
//                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_HEEL]))
//                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_HEEL]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_KNEE]))
                //허리와 각하체 지점들 추가
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ANKLE]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_FOOT_INDEX]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_HEEL]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_HEEL]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_KNEE]))
                // Cross body.
                embedding.add(subtract(lm[PoseLandmark.LEFT_KNEE], lm[PoseLandmark.RIGHT_KNEE]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_ANKLE], lm[PoseLandmark.RIGHT_ANKLE]))
                return embedding
            }
            EXERCISE_INFORMATION.UPPER ->{
                embedding.add(
                    subtract(
                        average(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.RIGHT_HIP]),
                        average(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_SHOULDER])
                    )
                )
                //shoulder
                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]))
                //elbow
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
                //hip
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_HIP], lm[PoseLandmark.LEFT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_HIP], lm[PoseLandmark.RIGHT_ELBOW]))

                // Cross body.
                embedding.add(subtract(lm[PoseLandmark.LEFT_PINKY], lm[PoseLandmark.RIGHT_PINKY]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))

                // Add Upper Weight.
                embedding.add(subtract(lm[PoseLandmark.LEFT_PINKY], lm[PoseLandmark.RIGHT_PINKY]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_ELBOW], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_WRIST], lm[PoseLandmark.RIGHT_ELBOW]))
                embedding.add(subtract(lm[PoseLandmark.LEFT_SHOULDER], lm[PoseLandmark.RIGHT_WRIST]))
                embedding.add(subtract(lm[PoseLandmark.RIGHT_SHOULDER], lm[PoseLandmark.LEFT_WRIST]))
                return embedding
            }
            else -> return embedding
        }

    }
}