package com.hnh.presentation.logic.classification


import android.util.Pair
import com.google.mlkit.vision.common.PointF3D
import com.google.mlkit.vision.pose.Pose
import com.hnh.presentation.logic.classification.Utils.maxAbs
import com.hnh.presentation.logic.classification.Utils.multiply
import com.hnh.presentation.logic.classification.Utils.multiplyAll
import com.hnh.presentation.logic.classification.Utils.subtract
import com.hnh.presentation.logic.classification.Utils.sumAbs
import java.util.*
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
        MAX_DISTANCE_TOP_K,
        MEAN_DISTANCE_TOP_K,
        AXES_WEIGHTS
    )

    fun confidenceRange(): Int {
        return Math.min(maxDistanceTopK, meanDistanceTopK)
    }

    fun classify(pose: Pose): ClassificationResult {
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
        val maxDistances = PriorityQueue(
            maxDistanceTopK
        ) { o1: Pair<PoseSample, Float?>, o2: Pair<PoseSample, Float?> ->
            -java.lang.Float.compare(
                o1.second!!, o2.second!!
            )
        }
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
                maxDistances.sortedBy { it.second }
                maxDistances.poll()
            }
        }

        // Keeps higher mean distances on top so we can pop it when top_k size is reached.
        val meanDistances = PriorityQueue(
            meanDistanceTopK
        ) { o1: Pair<PoseSample, Float?>, o2: Pair<PoseSample, Float?> ->
            -java.lang.Float.compare(
                o1.second!!, o2.second!!
            )
        }
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
                meanDistances.sortedBy{it.second}
                meanDistances.poll()
            }
        }
        for (sampleDistances in meanDistances) {
            val className: String = sampleDistances.first.getClassName()
            result.incrementClassConfidence(className)
        }
        return result
    }

    companion object {
        private const val TAG = "PoseClassifier"
        private const val MAX_DISTANCE_TOP_K = 30
        private const val MEAN_DISTANCE_TOP_K = 10

        // Note Z has a lower weight as it is generally less accurate than X & Y.
        private val AXES_WEIGHTS = PointF3D.from(1f, 1f, 0.5f)
        private fun extractPoseLandmarks(pose: Pose): MutableList<PointF3D> {
            val landmarks: MutableList<PointF3D> = ArrayList()
            for (poseLandmark in pose.allPoseLandmarks) {
                landmarks.add(poseLandmark.position3D)
            }
            return landmarks
        }
    }
}