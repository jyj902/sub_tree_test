package com.hnh.presentation.logic.classification

class RepetitionCounter @JvmOverloads constructor(
    val className: String,
    private val enterThreshold: Float = DEFAULT_ENTER_THRESHOLD,
    private val exitThreshold: Float = DEFAULT_EXIT_THRESHOLD
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
    @JvmName("getClassName1")
    fun getClassName(): String {
        return className
    }

    @JvmName("getNumRepeats1")
    fun getNumRepeats(): Int {
        return numRepeats
    }
    companion object {
        // These thresholds can be tuned in conjunction with the Top K values in {@link PoseClassifier}.
        // The default Top K value is 10 so the range here is [0-10].
        private const val DEFAULT_ENTER_THRESHOLD = 6f
        private const val DEFAULT_EXIT_THRESHOLD = 4f
    }
}