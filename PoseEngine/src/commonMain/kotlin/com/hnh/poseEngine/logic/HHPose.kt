package com.hnh.poseengine.logic

class HHPose(var landmarkPoseEngines: List<PoseLandmarkPoseEngine>) {
    fun getPoseLandmark(poseLandmarkType: Int): PoseLandmarkPoseEngine {
        return landmarkPoseEngines.get(poseLandmarkType)
    }

    val allPoseLandmarkPoseEngines: List<PoseLandmarkPoseEngine>
        get() = landmarkPoseEngines
}