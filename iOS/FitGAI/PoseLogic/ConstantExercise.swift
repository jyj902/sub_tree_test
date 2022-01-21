//
//  ConstantExercise.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation

class LOGIC {
    static let LOGIC = "Logic"
    static let READY = "Ready"
    static let ACTION = "Action"
}

class EXERCISE_INFORMATION{
    //세트, 운동상태 휴식시간 상수
    
    static var DEFAULT_REST_TIME:Int64 = Int64(40000)
    static var REPS_DEFAULT:Int = 20

    static let SET_FIRST_INT:Int32 = 1
    static let SET_SECOND_INT:Int32  = 2
    static let SET_THIRD_INT:Int32 = 3
    static let SET_FORTH_INT:Int32 = 4
    static let SET_FIFTH_INT:Int32 = 5
    static let STATE_EXERCISE_STOP:Int32 = 0
    static let STATE_EXERCISE_START:Int32 = 1
    static let STATE_REST:Int32 = 2
    // 운동정보 전체 사용 상수
    static let UPPER = 0
    static let LOWER = 1
    static let FULL = 2
    static let LONG_TIME = Int64(1000)
    static let MID_TIME = Int64(800)
    static let SHOUT_TIME = Int64(600)
    static let SET_DEFAULT = 3
    static let REPS_LONG = 30
    static let SET_LONG = 5
    static let REPS_SHOUT = 15
    static let SET_SHOUT = 2
    static let FIRST_SET = 99
    static let MODE_SET = 0
    static let MODE_SELECT_SET = 1
    static let MODE_FREE = 2
    
    class func initializeVariables() {
        switch FeatureConst.FEATURE_APP_LEVEL {
        case .debug:
            DEFAULT_REST_TIME = Int64(5000)
            REPS_DEFAULT = 3
        case .release:
            DEFAULT_REST_TIME = Int64(40000)
            REPS_DEFAULT = 20
        case .demo:
            DEFAULT_REST_TIME = Int64(15000)
            REPS_DEFAULT = 5
        }
    }
}

class DEAD_LIFT {
    static let DEAD_LIFT = "DeadLift"
    static let FOOT_DISTANCE = "FootDistance"
    static let HIP_ANGLE_DOWN = "HipAngleDown"
    static let HIP_ANGLE_UP = "HipAngleUp"
    static let BAR_WRIST = "BarWrist"
}

class SQUAT {
    static let SQUAT = "Squat"
    static let FOOT_TO_SHOULDER_DISTANCE = "FootToShoulderDistance"
    static let KNEE_POINT = "KneePoint"
    static let KNEE_DISTANCE = "KneeDistance"
    static let HIP_ANGLE = "HipAngle"
    static let KNEE_ANGLE_DOWN = "KneeAngleDown"
}

class SIDE_LATERAL_RAISE {
    static let SIDE_LATERAL_RAISE = "SideLateralRaise"
    static let WRIST_3D = "Wrist3D"
    static let SHOULDER_TO_WRIST = "ShoulderToWrist"
    static let ELBOW_ANGLE = "ElbowAngle"
    static let HIP_ANGLE = "HipAngle"
    static let ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    static let ELBOW_ANGLE_UP = "ElbowAngleUp"
}

class SHOULDER_PRESS {
    static let SHOULDER_PRESS = "ShoulderPress"
    static let DOWN_WRIST = "DownWrist"
    static let WRIST_3D = "Wrist3D"
    static let ELBOW_ANGLE = "ElbowAngle"
    static let ELBOW_UP = "ElbowUp"
    static let ELBOW_DOWN = "ElbowDown"
    static let ELBOW_FRONT = "ElbowFront"
}

class LAT_PULL_DOWN {
    static let LAT_PULL_DOWN = "LatPullDownBack"
    static let WRIST_BAR = "BarWrist"
    static let ELBOW_VERTICAL = "ElbowVertical"
    static let WRIST_DOWN = "WristDown"
    static let ELBOW_UP = "ElbowUp"
    static let HIP_ANGLE = "HipAngle"
}

class CABLE_LOW {
    static let CABLE_LOW = "CableLow"
}

class LUNGE {
    static let LUNGE = "Lunge"
    static let KNEE_ANGLE_DOWN = "KneeAngleDown"
    static let FOOT_TO_PELVIS_DISTANCE = "FootToPelvisDistance"
    static let KNEE_POINT = "LungeKneePoint"
    static let KNEE_TO_CENTER = "KneeToCenter"
    static let HIP_ANGLE = "HipAngle"
    static let FOOT_STRAIGHT = "FootStraight"
}

class CABLE_PUSH_DOWN {
    static let CABLE_PUSH_DOWN = "CablePushDown"
    static let ELBOW_FIX = "ElbowFix"
    static let HIP_FIX = "HipFix"
    static let ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    static let ELBOW_ANGLE_UP = "ElbowAngleUp"
}

class KICK_BACK_DUMBBELL {
    static let KICK_BACK_DUMBBELL = "KickBackDumbbell"
}

class STANDING_BARBELL_CURL {
    static let STANDING_BARBELL_CURL = "StandingBarbellCurl"
    static let ELBOW_FIX = "ElbowFix"
    static let HIP_FIX = "HipFix"
    static let ELBOW_ANGLE_DOWN = "ElbowAngleDown"
    static let ELBOW_ANGLE_UP = "ElbowAngleUp"
}
