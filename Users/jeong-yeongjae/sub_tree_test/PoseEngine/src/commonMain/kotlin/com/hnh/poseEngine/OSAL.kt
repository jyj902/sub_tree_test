package com.hnh.poseengine

//OSAdaptationLayer
interface OSAL {
    fun elapsedRealtime():Long
    fun playSound(soundName:String)
    fun playOnlyCountSound(count:Int)
    fun playCountSound(count:Int, param:String)
    fun logE(tag:String, log:String)
    fun logW(tag:String, log:String)
    fun logD(tag:String, log:String)
    fun logV(tag:String, log:String)
    fun loadSampleFileOpener(runModel: String) :String
    fun resetRepCounter()
    fun csvSaver(
        csvDataListFloat: ArrayList<Array<Float>>,
        exerciseName: String,
        nowSet: Int,
        counting: Int,
        repsAfter: Int
    )
    fun postFile(
        file: String,
        successCount: Int,
        failCount: Int,
        runningTime: Long,
        restTime: Long,
        isFreeMode: Boolean,
    )
    fun playLottieAnimation(animationId:Int)
    fun updateCount(count:Int, totalCount:Int)
}
