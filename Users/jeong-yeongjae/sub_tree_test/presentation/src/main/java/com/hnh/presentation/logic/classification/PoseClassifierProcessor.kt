package com.hnh.presentation.logic.classification

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.mlkit.vision.pose.Pose
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class PoseClassifierProcessor @WorkerThread constructor(
    context: Context,
    isStreamMode: Boolean,
    Exercise: Pair<String, Int>
) {
    private val isStreamMode: Boolean
    private var emaSmoothing: EMASmoothing? = null
    private var repCounters: MutableList<RepetitionCounter>? = null
    private var poseClassifier: PoseClassifier? = null
    private var lastRepResult: String? = null

    init {
        //Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper())
        this.isStreamMode = isStreamMode
        if (isStreamMode) {
            emaSmoothing = EMASmoothing()
            repCounters = ArrayList()
            lastRepResult = ""
        }
        type = Exercise.second
        CompanionExercise = Exercise
        loadPoseSamples(context, Exercise)
    }

    private fun loadPoseSamples(context: Context, Exercise: Pair<String, Int>) {
        val poseSamples: MutableList<PoseSample> = ArrayList()
        try {
            val exercise = POSE_SAMPLES_FILE + Exercise.first + ".csv"
            val reader = BufferedReader(
                InputStreamReader(context.assets.open(exercise))
            )
            var csvLine = reader.readLine()
            while (csvLine != null) {
                // If line is not a valid {@link PoseSample}, we'll get null and skip adding to the list.
                val poseSample = PoseSample.getPoseSample(csvLine, ",")
                if (poseSample != null) {
                    poseSamples.add(poseSample)
                }
                csvLine = reader.readLine()
            }
        } catch (e: IOException) {
            Log.e(
                TAG,
                "Error when loading pose samples.\n$e"
            )
        }
        poseClassifier = PoseClassifier(poseSamples, Exercise.second)
        if (isStreamMode) {
            for (className in POSE_CLASSES) {
                repCounters!!.add(RepetitionCounter(className))
            }
        }
    }

    /**
     * Given a new [Pose] input, returns a list of formatted [String]s with Pose
     * classification results.
     *
     *
     * Currently it returns up to 2 strings as following:
     * 0: PoseClass : X reps
     * 1: PoseClass : [0.0-1.0] confidence
     */
    fun resetRepCounter(){
        repCounters!!.clear()
        repCounters!!.add(RepetitionCounter(PUSHUPS_CLASS))
        resetRepCounter = false
    }
    @WorkerThread
    fun getPoseResult(pose: Pose): List<String?> {
        //Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper())
        val result: MutableList<String?> = ArrayList()
        var classification = poseClassifier!!.classify(pose)
        if (resetRepCounter) resetRepCounter()
        // Update {@link RepetitionCounter}s if {@code isStreamMode}.
        if (isStreamMode) {
            // Feed pose to smoothing even if no pose found.
            classification = emaSmoothing!!.getSmoothedResult(classification)

            // Return early without updating repCounter if no pose found.
            if (pose.allPoseLandmarks.isEmpty()) {
                result.add(lastRepResult)
                return result
            }
            for (repCounter in repCounters!!) {
                    repsBefore = repCounter.getNumRepeats()
                    repsAfter = repCounter.addClassificationResult(classification)
                if (repsAfter > repsBefore) {
                    // Play a fun beep when rep counter updates.
//                    val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
//                    tg.startTone(ToneGenerator.TONE_PROP_BEEP)
                    lastRepResult = java.lang.String.format(
                        Locale.US, "%s : %d reps", repCounter.getClassName(), repsAfter
//                        Locale.US, "%d reps",  repsAfter
                    )
                    break
                }
            }
            result.add(lastRepResult)
        }

        // Add maxConfidence class of current frame to result if pose is found.
        if (!pose.allPoseLandmarks.isEmpty()) {
            val maxConfidenceClass: String = classification.getMaxConfidenceClass()
            poseState = maxConfidenceClass
            val maxConfidenceClassResult = java.lang.String.format(
                Locale.US,
//                "%s : %.2f confidence",
                "%s : %.2f con",
                maxConfidenceClass, classification.getClassConfidence(maxConfidenceClass)
                        / poseClassifier!!.confidenceRange()
            )
            result.add(maxConfidenceClassResult)
        }
        return result
    }

    companion object {
        private const val TAG = "PoseClassifierProcessor"

        //        private const val POSE_SAMPLES_FILE = "RunModel/DeadLift_csvs_out.csv"
        private const val POSE_SAMPLES_FILE = "RunModel/"
        var type: Int? = null
        var CompanionExercise: Pair<String, Int>? = null
        var repsBefore = 0
        var repsAfter = 0
        var poseState = ""

        // Specify classes for which we want rep counting.
        // These are the labels in the given {@code POSE_SAMPLES_FILE}. You can set your own class labels
        // for your pose samples.
        private const val PUSHUPS_CLASS = "down"
        private const val SQUATS_CLASS = ""
        private val POSE_CLASSES = arrayOf(
//            PUSHUPS_CLASS, SQUATS_CLASS
            PUSHUPS_CLASS
        )
        var resetRepCounter = false
    }


}