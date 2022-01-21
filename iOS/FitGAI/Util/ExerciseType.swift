//
//  ExerciseType.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/08.
//

import Foundation

enum ExerciseType : Int, CaseIterable {
    case cableRow = 0
    case latPullDown
    case shoulderPress
    case lunge
    case squat
    case deadLift
    case sideLateralRaise
    case cablePushDown
    case standingBarbellCurl
    case none
    
    // MARK: - Public Method
    func getLongName() -> String {
        switch self {
        case .cableRow: return TODO("케이블로우 운동하기")
        case .latPullDown: return TODO("렛풀다운 운동하기")
        case .shoulderPress: return TODO("숄더프레스 운동하기")
        case .lunge: return TODO("런지 운동하기")
        case .squat: return TODO("스쿼트 운동하기")
        case .deadLift: return TODO("데드리프트 운동하기")
        case .sideLateralRaise: return TODO("사이드 레터럴 레이즈 운동하기")
        case .cablePushDown: return TODO("케이블푸쉬다운 운동하기")
        case .standingBarbellCurl: return TODO("스탠딩바벨컬 운동하기")
        case .none: return TODO("없음")
        }
    }

    func getIdName() -> String {
        switch self {
        case .cableRow: return CABLE_LOW.CABLE_LOW
        case .latPullDown: return LAT_PULL_DOWN.LAT_PULL_DOWN
        case .shoulderPress: return SHOULDER_PRESS.SHOULDER_PRESS
        case .lunge: return LUNGE.LUNGE
        case .squat: return SQUAT.SQUAT
        case .deadLift: return DEAD_LIFT.DEAD_LIFT
        case .sideLateralRaise: return SIDE_LATERAL_RAISE.SIDE_LATERAL_RAISE
        case .cablePushDown: return CABLE_PUSH_DOWN.CABLE_PUSH_DOWN
        case .standingBarbellCurl: return STANDING_BARBELL_CURL.STANDING_BARBELL_CURL
        case .none: return TODO("없음")
        }
    }
    
    func getExerciseType() -> Int {
        switch self {
        case .cableRow: return EXERCISE_INFORMATION.UPPER
        case .latPullDown: return EXERCISE_INFORMATION.UPPER
        case .shoulderPress: return EXERCISE_INFORMATION.UPPER
        case .lunge: return EXERCISE_INFORMATION.LOWER
        case .squat: return EXERCISE_INFORMATION.LOWER
        case .deadLift: return EXERCISE_INFORMATION.FULL
        case .sideLateralRaise: return EXERCISE_INFORMATION.UPPER
        case .cablePushDown: return EXERCISE_INFORMATION.UPPER
        case .standingBarbellCurl: return EXERCISE_INFORMATION.UPPER
        case .none: return EXERCISE_INFORMATION.UPPER
        }
    }
}
