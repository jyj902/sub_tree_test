package com.hnh.poseengine.logic

import com.hnh.poseengine.logic.classification.PoseClassifierProcessor
import kotlin.collections.HashMap


/**
 * Created by hyerim on 2021/09/24...
 */

interface PoseProcessInterface {
    fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean>
    fun classificationType(): PoseClassifierProcessor
    fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                 checkListSound: HashMap<String, Boolean>)
    fun checkPose(pose: HHPose, debugger: Boolean): String
}