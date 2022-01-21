package com.hnh.poseengine.logic

/**
 * Created by hyerim on 2021/09/30...
 */
const val KEY = "210906"

/** 운동 sound effect */
object EXERCISE {
    //운동 효과음
    const val ENDING_SOUND = "ending"

    //운동 시작, 종료, 이미 운동중, 운동상태가 아님
    const val START = "start"
    const val END = "end"
    const val ALREADY = "already"
    const val _NO = "no"
    const val LAT_PULL_DOWN_3SET_END = "lat_pull_down_3et_end"
    const val LUNGE_3SET_END = "lunge_3set_end"
    const val SQUAT_3SET_END = "squat_3set_end"
    const val SHOULDER_PRESS_3SET_END = "shoulder_press_3set_end"
    const val SIDE_LATERAL_RAISE_3SET_END = "side_lateral_raise_3set_end"

    //녹화 시작, 종료
    const val RECORDING_START = "recording_start"
    const val RECORDING_END = "recording_end"

    //휴식, 운동 진행세트
    const val REST = "rest"
    const val SET_FIRST = "set_first"
    const val SET_SECOND = "set_second"
    const val SET_THIRD = "set_third"
    const val REST_BEFORE_10 = "rest_before_10"
    const val REST_BEFORE_3 = "rest_before_3"

    //운동 평가
    object RATING{
        const val PERFECT_1 = "perfect_1"
        const val PERFECT_2 = "perfect_2"
        const val PERFECT_3 = "perfect_3"
        const val PERFECT_4 = "perfect_4"
        const val GOOD_1 = "good_1"
        const val GOOD_2 = "good_2"
        const val GOOD_3 = "good_3"
        const val GOOD_4 = "good_4"
        const val SOSO_1 = "soso_1"
        const val SOSO_2 = "soso_2"
        const val SOSO_3 = "soso_3"
    }
    const val RATING_1 = "rating_1"
    const val RATING_2 = "rating_2"
    const val RATING_3 = "rating_3"
    const val RATING_4 = "rating_4"
    const val END_PERFECT = "end_perfect"

    //공통적인 조언
    const val REPEAT = "repeat"
    const val MOVE = "move"
    const val FRONT = "front"
    const val FAST = "fast"
    const val SHRINK = "shrink"
    const val RELAXATION = "relaxation"

    //운동 상세 조언
    const val UP_TO_HEAD_DUMBBELL = "up_to_head_dumbbell"
    const val HEAD_DUMBBELL = "head_dumbbell"

    const val KNEES_TOES = "knees_toes"
    const val KNEE_DEGREE_90 = "knee_degree_90"
    const val KNEE_FOOT_ANGLE = "knee_foot_angle"

    const val SHOULDER_OVER = "shoulder_over"
    const val SPREAD_SHOULDER = "spread_shoulder"

    const val DOWN_WRIST = "down_wrist"
    const val UP_WRIST = "up_wrist"
    const val SIDE_HANDS = "side_hands"


    const val ARMS_STRAIGHT = "arms_straight"

    const val HIP = "hip"
    const val WAIST = "waist"
    const val HIP_VERTICAL = "hip_vertical"

    const val FOOT_PELVIS_DISTANCE = "foot_pelvis_distance"

    const val UP_ELBOW = "up_elbow"
    const val ELBOW_STRAIGHT = "elbow_straight"
    const val ELBOW_VERTICAL = "elbow_vertical"

    const val LONG_BAR = "long_bar"
    const val DOWN_BAR = "down_bar"

    const val FIX_HIP = "fix_hip"
    const val FIX_ELBOW = "fix_elbow"

    const val FOOT_STRAIGHT = "foot_straight"

    const val SPEED_GOOD= "알맞은 속도에요"
    const val SPEED_BAD= "속도가 빨라요"
}

object SOCIAL_TYPE {
    const val KAKAO = "KAKAO"
    const val NAVER = "NAVER"
    const val GOOGLE = "GOOGLE"
}

object URL{
    const val SKELETON = "http://hnhinc.co.kr:8080/record/personal?userId="
}

val DIFFICULTY = arrayOf("초급", "중급", "고급")
//
//object EXERCISE_TITLE {
//    const val SQUAT = "Squat"
//    const val LUNGE = "Lunge"
//    const val SIDE_LETERAL_RAISE = "SideLateralRaise"
//    const val SHOULDER_PRESS = "ShoulderPress"
//    const val LAT_PULL_DOWN = "LatPullDown"
//    const val CABLE_LOW = "CableLow"
//    const val CABLE_PUSH_DOWN = "CablePushDown"
//    const val STANDING_BARBELL_CURL = "StandingBarbellCurl"
//    const val DEAD_LIFT = "DeadLift"
//}