package com.hnh.presentation.util

object LOGIC{
    const val LOGIC = "Logic"
    const val READY = "Ready"
    const val ACTION = "Action"
}
object EXERCISE_INFORMATION{
    //세트, 운동상태 휴식시간 상수
    const val SET_FIRST_INT = 1
    const val SET_SECOND_INT  = 2
    const val SET_THIRD_INT = 3
    const val SET_FORTH_INT = 4
    const val SET_FIFTH_INT = 5
    const val DEFAULT_REST_TIME = 40000
    const val STATE_EXERCISE_STOP = 0
    const val STATE_EXERCISE_START = 1
    const val STATE_REST = 2
    const val STATE_EXERCISE_FINISH = 3
    // 운동정보 전체 사용 상수
    const val UPPER = 0
    const val LOWER = 1
    const val FULL = 2
    const val LONG_TIME = 1000
    const val MID_TIME = 800
    const val SHOUT_TIME = 600
    const val REPS_DEFAULT = 20
    const val SET_DEFAULT = 3
    const val REPS_LONG = 30
    const val SET_LONG = 5
    const val REPS_SHOUT = 15
    const val SET_SHOUT = 2
    const val FIRST_SET = 99
    const val MODE_SET = 0
    const val MODE_SELECT_SET = 1
    const val MODE_FREE = 2

    const val SETTING_MODE = 0
    const val SETTING_SET = 1
    const val SETTING_REPS = 2
    const val SETTING_REST_TIME = 3

}
object EXERCISE_LIST{
    const val DEAD_LIFT = "DeadLift"
    const val SQUAT = "Squat"
    const val SIDE_LATERAL_RAISE = "SideLateralRaise"
    const val SHOULDER_PRESS = "ShoulderPress"
    const val LAT_PULL_DOWN = "LatPullDownBack"
    const val CABLE_ROW = "CableRow"
    const val LUNGE = "Lunge"
    const val CABLE_PUSH_DOWN = "CablePushDown"
    const val KICK_BACK_DUMBBELL = "KickBackDumbbell"
    const val STANDING_BARBELL_CURL = "StandingBarbellCurl"
    const val LEG_RAISE = "LegRaise"

    fun constrainList(exerciseID: String) : Boolean{
        val exerciseList = arrayListOf<String>()
        var result = false
        exerciseList.add(DEAD_LIFT)
        exerciseList.add(SQUAT)
        exerciseList.add(SIDE_LATERAL_RAISE)
        exerciseList.add(SHOULDER_PRESS)
        exerciseList.add(LAT_PULL_DOWN)
        exerciseList.add(CABLE_ROW)
        exerciseList.add(LUNGE)
        exerciseList.add(CABLE_PUSH_DOWN)
        exerciseList.add(KICK_BACK_DUMBBELL)
        exerciseList.add(STANDING_BARBELL_CURL)
        for(ID in exerciseList){
            if(ID == exerciseID){
                result =  true
            }
        }
        return result
    }
}

object DEAD_LIFT{
    const val FOOT_DISTANCE = "FootDistance"
    const val HIP_ANGLE_DOWN = "HipAngleDown"
    const val HIP_ANGLE_UP = "HipAngleUp"
    const val BAR_WRIST = "BarWrist"
}

object SQUAT{
    const val FOOT_TO_SHOULDER_DISTANCE = "FootToShoulderDistance"
    const val KNEE_POINT = "KneePoint"
    const val KNEE_DISTANCE = "KneeDistance"
    const val HIP_ANGLE = "HipAngle"
    const val KNEE_ANGLE_DOWN = "KneeAngleDown"
}

object SIDE_LATERAL_RAISE{
    const val WRIST_3D = "Wrist3D"
    const val SHOULDER_TO_WRIST = "ShoulderToWrist"
    const val ELBOW_ANGLE = "ElbowAngle"
    const val HIP_ANGLE = "HipAngle"
    const val ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    const val ELBOW_ANGLE_UP = "ElbowAngleUp"
}

object SHOULDER_PRESS{
    const val DOWN_WRIST = "DownWrist"
    const val WRIST_3D = "Wrist3D"
    const val ELBOW_ANGLE = "ElbowAngle"
    const val ELBOW_UP = "ElbowUp"
    const val ELBOW_DOWN = "ElbowDown"
    const val ELBOW_FRONT = "ElbowFront"
}

object LAT_PULL_DOWN{
    const val WRIST_BAR = "BarWrist"
    const val ELBOW_VERTICAL = "ElbowVertical"
    const val WRIST_DOWN = "WristDown"
    const val ELBOW_UP = "ElbowUp"
    const val HIP_ANGLE = "HipAngle"
}

object CABLE_ROW{
}

object LUNGE{
    const val KNEE_ANGLE_DOWN = "KneeAngleDown"
    const val FOOT_TO_PELVIS_DISTANCE = "FootToPelvisDistance"
    const val KNEE_POINT = "LungeKneePoint"
    const val KNEE_TO_CENTER = "KneeToCenter"
    const val HIP_ANGLE = "HipAngle"
    const val FOOT_STRAIGHT = "FootStraight"
}

object CABLE_PUSH_DOWN{
    const val ELBOW_FIX = "ElbowFix"
    const val HIP_FIX = "HipFix"
    const val ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    const val ELBOW_ANGLE_UP = "ElbowAngleUp"
}

object KICK_BACK_DUMBBELL{
}

object STANDING_BARBELL_CURL{
    const val ELBOW_FIX = "ElbowFix"
    const val HIP_FIX = "HipFix"
    const val ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    const val ELBOW_ANGLE_UP = "ElbowAngleUp"
}

object LEG_RAISE{
    const val HIP_ANGLE_DOWN = "HipAngleDown"
    const val HIP_ANGLE_UP = "HipAngleUp"
}