package com.hnh.poseengine.logic.classification

class RepetitionCounter constructor(
    val className: String,
    private val enterThreshold: Float = 6f, //DEFAULT_ENTER_THRESHOLD,
    private val exitThreshold: Float = 4f //DEFAULT_EXIT_THRESHOLD
) {
    var numRepeats = 0
        private set
    private var poseEntered = false

    fun addClassificationResult(classificationResult: ClassificationResult): Int {
        val poseConfidence = classificationResult.getClassConfidence(className)
        if (!poseEntered) {
            poseEntered = poseConfidence > enterThreshold
            return numRepeats
        }
        if (poseConfidence < exitThreshold) {
            numRepeats++
            poseEntered = false
        }
        return numRepeats
    }

    fun _getNumRepeats(): Int {
        return numRepeats
    }
    companion object {
//        // These thresholds can be tuned in conjunction with the Top K values in {@link PoseClassifier}.
//        // The default Top K value is 10 so the range here is [0-10].
//        private const val DEFAULT_ENTER_THRESHOLD = 6f
//        private const val DEFAULT_EXIT_THRESHOLD = 4f
    }
}