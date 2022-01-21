package com.hnh.poseengine.logic.classification

import com.hnh.poseengine.logic.PointF3D

/**
 * Reads Pose samples from a csv file.
 */
class PoseSample(val name: String, val className: String, landmarks: List<PointF3D>, val exerciseType: Int) {
    var embedding: PoseEmbedding = PoseEmbedding()
    var landark = landmarks

    fun _getClassName(): String {
        return className
    }

    fun getEmbedding(): List<PointF3D> {
        return embedding.getPoseEmbedding(landark, exerciseType)
    }


    companion object {
    }
}