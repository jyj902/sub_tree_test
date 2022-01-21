package com.hnh.poseengine.logic.classification

import com.hnh.poseengine.logic.Math
import com.hnh.poseengine.logic.PointF3D
import com.hnh.poseengine.logic.HHPose
import com.hnh.poseengine.logic.classification.Utils.maxAbs
import com.hnh.poseengine.logic.classification.Utils.multiply
import com.hnh.poseengine.logic.classification.Utils.multiplyAll
import com.hnh.poseengine.logic.classification.Utils.subtract
import com.hnh.poseengine.logic.classification.Utils.sumAbs
import kotlin.math.max


/**
 * Classifies {link Pose} based on given [PoseSample]s.
 *
 *
 * Inspired by K-Nearest Neighbors Algorithm with outlier filtering.
 * https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm
 */
class PoseClassifier(
    private val poseSamples: List<PoseSample>,
    private val Type: Int,
    private val maxDistanceTopK: Int,
    private val meanDistanceTopK: Int,
    private val axesWeights: PointF3D
) {
    constructor(poseSamples: List<PoseSample>, Type: Int) : this(
        poseSamples,
        Type,
        30, //MAX_DISTANCE_TOP_K,
        10, //MEAN_DISTANCE_TOP_K,
        PointF3D.from(1f, 1f, 0.5f) //AXES_WEIGHTS
    )

    private fun extractPoseLandmarks(pose: HHPose): MutableList<PointF3D> {
        val landmarks: MutableList<PointF3D> = ArrayList()
        for (poseLandmark in pose.allPoseLandmarkPoseEngines) {
            landmarks.add(poseLandmark.position3D)
        }
        return landmarks
    }

    fun confidenceRange(): Int {
        return Math.min(maxDistanceTopK, meanDistanceTopK)
    }

    fun classify(pose: HHPose): ClassificationResult {
        return classify(extractPoseLandmarks(pose))
    }

    fun classify(landmarks: MutableList<PointF3D>): ClassificationResult {
        val result = ClassificationResult()
        // Return early if no landmarks detected.
        if (landmarks.isEmpty()) {
            return result
        }

        // We do flipping on X-axis so we are horizontal (mirror) invariant.
        var flippedLandmarks: MutableList<PointF3D> = ArrayList(landmarks)
        val poseembedding = PoseEmbedding()
        multiplyAll(flippedLandmarks, PointF3D.from(-1F, 1F, 1F))
        val embedding: List<PointF3D> = poseembedding.getPoseEmbedding(landmarks, Type)
        val flippedEmbedding: List<PointF3D> =
            poseembedding.getPoseEmbedding(flippedLandmarks, Type)


        // Classification is done in two stages:
        //  * First we pick top-K samples by MAX distance. It allows to remove samples that are almost
        //    the same as given pose, but maybe has few joints bent in the other direction.
        //  * Then we pick top-K samples by MEAN distance. After outliers are removed, we pick samples
        //    that are closest by average.

        // Keeps max distance on top so we can pop it when top_k size is reached.
        val maxDistances = ArrayList<Pair<PoseSample, Float?>>()
        // Retrieve top K poseSamples by least distance to remove outliers.
        for (poseSample in poseSamples) {
            val sampleEmbedding: List<PointF3D> = poseSample.getEmbedding()
            var originalMax = 0f
            var flippedMax = 0f
            for (i in embedding.indices) {
                originalMax = max(
                    originalMax,
                    maxAbs(multiply(subtract(embedding[i], sampleEmbedding[i]), axesWeights))
                )
                flippedMax = max(
                    flippedMax,
                    maxAbs(
                        multiply(
                            subtract(flippedEmbedding[i], sampleEmbedding[i]), axesWeights
                        )
                    )
                )
            }
            // Set the max distance as min of original and flipped max distance.
            maxDistances.add(Pair(poseSample, Math.min(originalMax, flippedMax)))
            // We only want to retain top n so pop the highest distance.
            if (maxDistances.size > maxDistanceTopK) {
                maxDistances.sortBy { it.second }
                maxDistances.removeLast()
            }
        }

        // Keeps higher mean distances on top so we can pop it when top_k size is reached.
        val meanDistances = ArrayList<Pair<PoseSample, Float?>>()
        // Retrive top K poseSamples by least mean distance to remove outliers.
        for (sampleDistances in maxDistances) {
            val poseSample = sampleDistances.first
            val sampleEmbedding: List<PointF3D> = poseSample.getEmbedding()
            var originalSum = 0f
            var flippedSum = 0f
            for (i in embedding.indices) {
                originalSum += sumAbs(
                    multiply(
                        subtract(embedding[i], sampleEmbedding[i]), axesWeights
                    )
                )
                flippedSum += sumAbs(
                    multiply(subtract(flippedEmbedding[i], sampleEmbedding[i]), axesWeights)
                )
            }
            // Set the mean distance as min of original and flipped mean distances.
            val meanDistance = Math.min(originalSum, flippedSum) / (embedding.size * 2)
            meanDistances.add(Pair(poseSample, meanDistance))
            // We only want to retain top k so pop the highest mean distance.
            if (meanDistances.size > meanDistanceTopK) {
                meanDistances.sortBy { it.second }
                meanDistances.removeLast()
            }
        }
        for (sampleDistances in meanDistances) {
            val className: String = sampleDistances.first._getClassName()
            result.incrementClassConfidence(className)
        }
        return result
    }

    companion object {
//        private const val TAG = "PoseClassifier"
//        private const val MAX_DISTANCE_TOP_K = 30
//        private const val MEAN_DISTANCE_TOP_K = 10
//
//        // Note Z has a lower weight as it is generally less accurate than X & Y.
//        private val AXES_WEIGHTS = PointF3D.from(1f, 1f, 0.5f)
//        private fun extractPoseLandmarks(pose: HHPose): MutableList<PointF3D> {
//            val landmarks: MutableList<PointF3D> = ArrayList()
//            for (poseLandmark in pose.allPoseLandmarkPoseEngines) {
//                landmarks.add(poseLandmark.position3D)
//            }
//            return landmarks
//        }
    }
}