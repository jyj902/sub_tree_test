package com.hnh.poseengine.logic.classification

import com.hnh.poseengine.OSAL
import com.hnh.poseengine.logic.ExerciseGlobal
import com.hnh.poseengine.logic.HHPose
import com.hnh.poseengine.logic.PointF3D

class PoseClassifierProcessor constructor(val osal: OSAL, val exerciseGlobal: ExerciseGlobal,
                                          isStreamMode: Boolean,
                                          Exercise: Pair<String, Int>
) {
    private val isStreamMode: Boolean
    private var emaSmoothing: EMASmoothing? = null
    private var repCounters: MutableList<RepetitionCounter>? = null
    private var poseClassifier: PoseClassifier? = null
    private var lastRepResult: String? = null
    val EXERCISE_CLASS = "PUSH_UP"
    val EXERCISE_COUNT = "down"
    private val TAG = "PoseSample"
    private val NUM_LANDMARKS = 33
    private val NUM_DIMS = 3
    init {
        //Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper())
        this.isStreamMode = isStreamMode
        if (isStreamMode) {
            emaSmoothing = EMASmoothing()
            repCounters = ArrayList()
            lastRepResult = ""
        }
//        type = Exercise.second
//        CompanionExercise = Exercise
        loadPoseSamples(Exercise)
    }

    fun getPoseSample(csvLine: String?, separator: String?, exerciseType : Int): PoseSample? {
        if (csvLine == null || separator == null) {
            return null
        }
        val tokens = csvLine.split(separator)
        // Format is expected to be Name,Class,X1,Y1,Z1,X2,Y2,Z2...
        // + 2 is for Name & Class.
        if (tokens.size != NUM_LANDMARKS * NUM_DIMS + 2) {
            println("Invalid number of tokens for PoseSample")
            return null
        }
        val name = tokens[0]
        val className = tokens[1]
        val landmarks: MutableList<PointF3D> = ArrayList()
        // Read from the third token, first 2 tokens are name and class.
        var i = 2
        while (i < tokens.size) {
            try {
                landmarks.add(
                    PointF3D.from(
                        tokens[i].toFloat(),
                        tokens[i + 1].toFloat(),
                        tokens[i + 2].toFloat()
                    )
                )
            } catch (e: NullPointerException) {
                println("Invalid value " + tokens[i] + " for landmark position.")
                return null
            } catch (e: NumberFormatException) {
                println("Invalid value " + tokens[i] + " for landmark position.")
                return null
            }
            i += NUM_DIMS
        }
        return PoseSample(name, className, landmarks, exerciseType)
    }

    private fun loadPoseSamples(Exercise: Pair<String, Int>) {
        //NEED_PORT---------------------------
        val poseSamples: MutableList<PoseSample> = ArrayList()
        val exercise = Exercise.first
//        val reader = BufferedReader(
//            InputStreamReader(context.assets.open(exercise))
//        )
        val reder = osal.loadSampleFileOpener(exercise)
        val csvLine = reder.split("\n")
        for (rowData in csvLine) {
            val poseSample = getPoseSample(rowData, ",", Exercise.second)
            if (poseSample != null) {
                poseSamples.add(poseSample)
            }
        }
        poseClassifier = PoseClassifier(poseSamples, Exercise.second)
        if (isStreamMode) {
            repCounters!!.add(RepetitionCounter(EXERCISE_COUNT))
//            for (className in POSE_CLASSES) {
//                repCounters!!.add(RepetitionCounter(className))
//            }
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
    fun getPoseResult(pose: HHPose): List<String?> {
        //Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper())
        val result: MutableList<String?> = ArrayList()
        var classification = poseClassifier!!.classify(pose)

        // Update {@link RepetitionCounter}s if {@code isStreamMode}.
        if (isStreamMode) {
            // Feed pose to smoothing even if no pose found.
            classification = emaSmoothing!!.getSmoothedResult(classification)

            // Return early without updating repCounter if no pose found.
            if (pose.allPoseLandmarkPoseEngines.isEmpty()) {
                result.add(lastRepResult)
                return result
            }
            for (repCounter in repCounters!!) {
                exerciseGlobal.repsBefore = repCounter._getNumRepeats()
                exerciseGlobal.repsAfter = repCounter.addClassificationResult(classification)
                if (exerciseGlobal.repsAfter > exerciseGlobal.repsBefore) {
                    // Play a fun beep when rep counter updates.
//                    val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
//                    tg.startTone(ToneGenerator.TONE_PROP_BEEP)
                    lastRepResult = "$repCounter.getClassName() $exerciseGlobal.repsAfter reps"
//                        Locale.US, "%d reps",  repsAfter
                    break
                }
            }
            result.add(lastRepResult)
        }

        // Add maxConfidence class of current frame to result if pose is found.
        if (!pose.allPoseLandmarkPoseEngines.isEmpty()) {
            val maxConfidenceClass: String = classification.getMaxConfidenceClass()
            exerciseGlobal.poseState = maxConfidenceClass
            val maxConfidenceClassResult = "$maxConfidenceClass : ${classification.getClassConfidence(maxConfidenceClass) / poseClassifier!!.confidenceRange()} con"
            result.add(maxConfidenceClassResult)
        }
        return result
    }

    companion object {
////        private const val TAG = "PoseClassifierProcessor"
//        var type: Int? = null
//        var CompanionExercise: Pair<String, Int>? = null
//        private const val PUSHUPS_CLASS = "down"
//        private const val SQUATS_CLASS = ""
//        private val POSE_CLASSES = arrayOf(
////            PUSHUPS_CLASS, SQUATS_CLASS
//            PUSHUPS_CLASS
//        )
    }


}