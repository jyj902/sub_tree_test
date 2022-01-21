package com.hnh.poseengine.logic

class PoseLandmarkPoseEngine(var landmarkType:Int, var position3D: PointF3D, var position: PointF, var inFrameLikelihood:Float) {
    companion object {
        val NOSE = 0
        val LEFT_EYE_INNER = 1
        val LEFT_EYE = 2
        val LEFT_EYE_OUTER = 3
        val RIGHT_EYE_INNER = 4
        val RIGHT_EYE = 5
        val RIGHT_EYE_OUTER = 6
        val LEFT_EAR = 7
        val RIGHT_EAR = 8
        val LEFT_MOUTH = 9
        val RIGHT_MOUTH = 10
        val LEFT_SHOULDER = 11
        val RIGHT_SHOULDER = 12
        val LEFT_ELBOW = 13
        val RIGHT_ELBOW = 14
        val LEFT_WRIST = 15
        val RIGHT_WRIST = 16
        val LEFT_PINKY = 17
        val RIGHT_PINKY = 18
        val LEFT_INDEX = 19
        val RIGHT_INDEX = 20
        val LEFT_THUMB = 21
        val RIGHT_THUMB = 22
        val LEFT_HIP = 23
        val RIGHT_HIP = 24
        val LEFT_KNEE = 25
        val RIGHT_KNEE = 26
        val LEFT_ANKLE = 27
        val RIGHT_ANKLE = 28
        val LEFT_HEEL = 29
        val RIGHT_HEEL = 30
        val LEFT_FOOT_INDEX = 31
        val RIGHT_FOOT_INDEX = 32
    }
//
//    fun getInFrameLikelihood(): Float {
//        return inFrameLikelihood
//    }
//
//    fun getLandmarkType(): Int {
//        return landmarkType
//    }
//
//    fun getPosition(): PointF {
//        return position
//    }
//
//    fun getPosition3D(): PointF3D {
//        return position3D
//    }
}