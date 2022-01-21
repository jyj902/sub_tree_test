package com.hnh.presentation.logic.classification

import java.util.*
import java.util.concurrent.LinkedBlockingDeque


/**
 * Runs EMA smoothing over a window with given stream of pose classification results.
 */
class EMASmoothing @JvmOverloads constructor(
    private val windowSize: Int = DEFAULT_WINDOW_SIZE,
    private val alpha: Float = DEFAULT_ALPHA
) {
    // This is a window of {@link ClassificationResult}s as outputted by the {@link PoseClassifier}.
    // We run smoothing over this window of size {@link windowSize}.
    private val window: Deque<ClassificationResult>
    fun getSmoothedResult(classificationResult: ClassificationResult): ClassificationResult {
        // If we are at window size, remove the last (oldest) result.
        if (window.size == windowSize) {
            window.pollLast()
        }
        // Insert at the beginning of the window.
        window.addFirst(classificationResult)
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
        private const val DEFAULT_WINDOW_SIZE = 10
        private const val DEFAULT_ALPHA = 0.2f
    }

    init {
        window = LinkedBlockingDeque(windowSize)
    }
}