package com.hnh.poseengine.logic.classification



/**
 * Runs EMA smoothing over a window with given stream of pose classification results.
 */
class EMASmoothing constructor(
    private val windowSize: Int = 10,
    private val alpha: Float = 0.2f
) {
    // This is a window of {@link ClassificationResult}s as outputted by the {@link PoseClassifier}.
    // We run smoothing over this window of size {@link windowSize}.
    private val window: ArrayList<ClassificationResult> = ArrayList<ClassificationResult>()
    fun getSmoothedResult(classificationResult: ClassificationResult): ClassificationResult {
        // If we are at window size, remove the last (oldest) result.
        if (window.size == windowSize) {
            window.removeFirst()
        }
        // Insert at the beginning of the window.
        window.add(classificationResult)
        val allClasses: MutableSet<String> = HashSet()
        for (result in window) {
            allClasses.addAll(result.getAllClasses())
        }
        val smoothedResult = ClassificationResult()
        for (className in allClasses) {
            var factor = 1f
            var topSum = 0f
            var bottomSum = 0f
            for (result in window) {
                val value = result.getClassConfidence(className)
                topSum += factor * value
                bottomSum += factor
                factor = (factor * (1.0 - alpha)).toFloat()
            }
            smoothedResult.putClassConfidence(className, topSum / bottomSum)
        }
        return smoothedResult
    }

    companion object {
//        private const val DEFAULT_WINDOW_SIZE = 10
//        private const val DEFAULT_ALPHA = 0.2f
    }
}