package com.hnh.poseengine.logic
class ExercisePose(
    var exerciseFlag : Int, // 1 운동중/ 0 휴식/ -1 test
    var nowSets : Int = -1, //현재 세트 수
    var defaultCount : Int = -1, // 정해진 운동 횟수
    var allCount : Int = -1, //전체 운동 횟수
    var successCount : Int = -1, //성공 횟수
    var failCount : Int = -1, //실패 횟수
    var pose: String = "", //뭐였지
    var speed : String = "",// 속도
    var restTime : Int = -1,// 쉬는시간 안내
    var testMessage : String = ""  // testMessage
) {}