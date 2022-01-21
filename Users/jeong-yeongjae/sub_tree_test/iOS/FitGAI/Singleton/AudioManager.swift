//
//  TTS.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/07.
//

import UIKit
import AVKit
import AVFAudio
import AVFoundation

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum AudioEnum {
    //운동 시작, 종료, 이미 운동중, 운동상태가 아님
    case start
    case end
    case already
    case no
    
    //운동별 종료
    case squat3SetEnd
    case lunge3SetEnd
    case latPullDown3SetEnd
    case sideLateralRaise3SetEnd
    case shoulderPress3SetEnd
    
    //휴식, 운동 진행세트
    case rest
    case setFirst
    case setSecond
    case setThird
    case restBefore10
    case restBefore3
    
    //녹화 시작, 종료
    case recordingStart
    case recordingEnd
    
    //운동 평가
    case rating1
    case rating2
    case rating3
    case endPerfect
    
    //공통적인 조언
    case `repeat`
    case move
    case front
    case fast
    case shrink
    case relaxation
    
    //운동 상세 조언
    case kneesToes
    case armsStraight
    case shoulderOver
    case sideHands
    case spreadShoulder
    case hip
    case waist
    case headDumbbell
    case uptoHeadDumbbell
    case kneeDegree90
    case kneeFootAngle
    case downWrist
    case upWrist
    case hipVertical
    case footPelvisDistance
    case upElbow
    case elbowStraight
    case elbowVertical
    case longBar
    case downBar
    case fixElbow
    case fixHip
    
    case count_01
    case count_02
    case count_03
    case count_04
    case count_05
    case count_06
    case count_07
    case count_08
    case count_09
    case count_10
    case count_11
    case count_12
    case count_13
    case count_14
    case count_15
    case count_16
    case count_17
    case count_18
    case count_19
    case count_20
    case count_21
    case count_22
    case count_23
    case count_24
    case count_25
    case count_26
    case count_27
    case count_28
    case count_29
    case count_30
    case none

    // MARK: - Public Method
    func getSentense() -> String {
        switch self {
        //운동 시작, 종료, 이미 운동중, 운동상태가 아님
        case .start : return TODO("운동을 시작합니다.")
        case .end : return TODO("운동을 종료합니다.")
        case .already : return TODO("이미 운동중입니다.")
        case .no : return TODO("운동 상태가 아닙니다.")
        
        //운동별 종료
        case .squat3SetEnd : return TODO("스쿼트 3세트 종료 수고했어요.")
        case .lunge3SetEnd : return TODO("런지 3세트 종료 수고했어요.")
        case .latPullDown3SetEnd : return TODO("랫플다운 3세트 종료 수고했어요.")
        case .sideLateralRaise3SetEnd : return TODO("사이드 레터럴 레이즈 3세트 종료 수고했어요.")
        case .shoulderPress3SetEnd : return TODO("숄더 프레스 3세트 종료 수고했어요.")
        
        //휴식, 운동 진행세트
        case .rest : return TODO("휴식 하세요.")
        case .setFirst : return TODO("1세트 시작합니다.")
        case .setSecond : return TODO("2세트 시작합니다.")
        case .setThird : return TODO("3세트 시작합니다.")
        case .restBefore10 : return TODO("휴식 시간 10초 남았어요. 준비해 주세요.")
        case .restBefore3 : return TODO("휴식 시간 종료. 준비해 주세요.")
            
        //녹화 시작, 종료
        case .recordingStart : return TODO("녹화를 시작합니다.")
        case .recordingEnd : return TODO("녹화를 종료합니다.")

        //운동 평가
        case .rating1 : return TODO("아주 잘했어요")
        case .rating2 : return TODO("잘했어요")
        case .rating3 : return TODO("나쁘지않아요")
        case .endPerfect : return TODO("성공적인 운동이었어요")

        //공통적인 조언
        case .`repeat` : return TODO("다시하세요")
        case .move : return TODO("움직이세요")
        case .front : return TODO("정면을 바라봐주세요")
        case .fast : return TODO("속도가 빠릅니다")
        case .shrink : return TODO("끝까지 수축해주세요")
        case .relaxation : return TODO("끝까지 이완해주세요")
            
        //운동 상세 조언
        case .kneesToes : return TODO("무릎을 발끝으로 향해 주세요")
        case .armsStraight : return TODO("팔을 일자로 피세요")
        case .shoulderOver : return TODO("어깨위로 손을 올리지 마세요")
        case .sideHands : return TODO("팔꿈치를 옆으로 들어 주세요.")
        case .spreadShoulder : return TODO("어깨 넓이로 벌려주세요")
        case .hip : return TODO("엉덩이를 뒤로 빼주세요")
        case .waist : return TODO("허리를 펴주세요")
        case .headDumbbell : return TODO("팔꿈치를 90도로 유지해 주세요.")
        case .uptoHeadDumbbell : return TODO("덤벨을 머리위로 들어 주세요.")
        case .kneeDegree90 : return TODO("무릎을 90도까지 앉으세요")
        case .kneeFootAngle : return TODO("앞쪽 무릎을 발끝으로 뻗어 주세요.")
        case .downWrist : return TODO("덤벨을 더 내려주세요")
        case .upWrist : return TODO("덤벨을 끝까지 밀어주세요")
        case .hipVertical : return TODO("수직으로 앉아주세요")
        case .footPelvisDistance : return TODO("다리를 골반넓이로 벌려주세요")
        case .upElbow : return TODO("팔꿈치를 올려주세요")
        case .elbowStraight : return TODO("팔꿈치를 끝까지 펴주세요")
        case .elbowVertical : return TODO("팔꿈치를 일직선으로 내려주세요")
        case .longBar : return TODO("바를 더 넓게 잡으세요")
        case .downBar : return TODO("바를 더 내려주세요")
        case .fixElbow : return TODO("팔꿈치 고정해 주세요.")
        case .fixHip : return TODO("허리 고정해 주세요.")
        case .count_01 : return TODO("한개")
        case .count_02 : return TODO("두개")
        case .count_03 : return TODO("세개")
        case .count_04 : return TODO("네개")
        case .count_05 : return TODO("다섯개")
        case .count_06 : return TODO("여섯개")
        case .count_07 : return TODO("일곱개")
        case .count_08 : return TODO("여덟개")
        case .count_09 : return TODO("아홉개")
        case .count_10 : return TODO("열개")
        case .count_11 : return TODO("열한개")
        case .count_12 : return TODO("열두개")
        case .count_13 : return TODO("열세개")
        case .count_14 : return TODO("열네개")
        case .count_15 : return TODO("열다섯개")
        case .count_16 : return TODO("열여섯개")
        case .count_17 : return TODO("열일곱개")
        case .count_18 : return TODO("열여덟개")
        case .count_19 : return TODO("열아홉개")
        case .count_20 : return TODO("스무개")
        case .count_21 : return TODO("스물한개")
        case .count_22 : return TODO("스물두개")
        case .count_23 : return TODO("스물세개")
        case .count_24 : return TODO("스물네개")
        case .count_25 : return TODO("스물다섯개")
        case .count_26 : return TODO("스물여섯개")
        case .count_27 : return TODO("스물일곱개")
        case .count_28 : return TODO("스물여덟개")
        case .count_29 : return TODO("스물아홉개")
        case .count_30 : return TODO("서른개")
        case .none: return TODO("없음")
        }
    }
    
    // MARK: - Public Method
    func getFileName() -> String {
        switch self {
        //운동 시작, 종료, 이미 운동중, 운동상태가 아님
        case .start : return "exercise_start"
        case .end : return "exercise_end"
        case .already : return "exercise_already"
        case .no : return "exercise_no"
        
        //운동별 종료
        case .squat3SetEnd : return "exercise_squat_3set_end"
        case .lunge3SetEnd : return "exercise_lunge_3set_end"
        case .latPullDown3SetEnd : return "exercise_lat_pull_down_3set_end"
        case .sideLateralRaise3SetEnd : return "exercise_side_lateral_raise_3set_end"
        case .shoulderPress3SetEnd : return "exercise_shoulder_press_3set_end"
        
        //휴식, 운동 진행세트
        case .rest : return "exercise_rest"
        case .setFirst : return "exercise_set_first"
        case .setSecond : return "exercise_set_second"
        case .setThird : return "exercise_set_third"
        case .restBefore10 : return "exercise_rest_before_10"
        case .restBefore3 : return "exercise_rest_before_3"
        //녹화 시작, 종료
        case .recordingStart : return "exercise_recording"
        case .recordingEnd : return "exercise_recording_stop"
        
        //운동 평가
        case .rating1 : return "exercise_perfect"
        case .rating2 : return "exercise_good"
        case .rating3 : return "exercise_soso"
        case .endPerfect : return "exercise_end_perfect"
        
        //공통적인 조언
        case .`repeat` : return "exercise_repeat"
        case .move : return "exercise_move"
        case .front : return "exercise_front"
        case .fast : return "exercise_fast"
        case .shrink : return "exercise_shrink"
        case .relaxation : return "exercise_relaxation"
        
        //운동 상세 조언
        case .kneesToes : return "exercise_knees_toes"
        case .armsStraight : return "exercise_arms_straight"
        case .shoulderOver : return "exercise_shoulder_over"
        case .sideHands : return "exercise_side_hands"
        case .spreadShoulder : return "exercise_spread_shoulder"
        case .hip : return "exercise_hip"
        case .waist : return "exercise_waist"
        case .headDumbbell : return "exercise_head_dumbbell"
        case .uptoHeadDumbbell : return "exercise_up_to_head_dumbbell"
        case .kneeDegree90 : return "exercise_knee_degree_90"
        case .kneeFootAngle : return "exercise_knee_foot_angle"
        case .downWrist : return "exercise_down_wrist"
        case .upWrist : return "exercise_up_wrist"
        case .hipVertical : return "exercise_hip_vertical"
        case .footPelvisDistance : return "exercise_foot_pelvis_distance"
        case .upElbow : return "exercise_up_elbow"
        case .elbowStraight : return "exercise_elbow_straight"
        case .elbowVertical : return "exercise_elbow_vertical"
        case .longBar : return "exercise_long_bar"
        case .downBar : return "exercise_down_bar"
        case .fixElbow : return "exercise_fix_elbow"
        case .fixHip : return "exercise_fix_hip"
        case .count_01 : return "exercise_count_1"
        case .count_02 : return "exercise_count_2"
        case .count_03 : return "exercise_count_3"
        case .count_04 : return "exercise_count_4"
        case .count_05 : return "exercise_count_5"
        case .count_06 : return "exercise_count_6"
        case .count_07 : return "exercise_count_7"
        case .count_08 : return "exercise_count_8"
        case .count_09 : return "exercise_count_9"
        case .count_10 : return "exercise_count_10"
        case .count_11 : return "exercise_count_11"
        case .count_12 : return "exercise_count_12"
        case .count_13 : return "exercise_count_13"
        case .count_14 : return "exercise_count_14"
        case .count_15 : return "exercise_count_15"
        case .count_16 : return "exercise_count_16"
        case .count_17 : return "exercise_count_17"
        case .count_18 : return "exercise_count_18"
        case .count_19 : return "exercise_count_19"
        case .count_20 : return "exercise_count_20"
        case .count_21 : return "exercise_count_21"
        case .count_22 : return "exercise_count_22"
        case .count_23 : return "exercise_count_23"
        case .count_24 : return "exercise_count_24"
        case .count_25 : return "exercise_count_25"
        case .count_26 : return "exercise_count_26"
        case .count_27 : return "exercise_count_27"
        case .count_28 : return "exercise_count_28"
        case .count_29 : return "exercise_count_29"
        case .count_30 : return "exercise_count_30"
        case .none: return "none"
        }
    }
}

// TTS 는 중간에 끊는 것도 안 되고, Queue 에 쌓이는 것처럼 동작합니다. 그리고 didFinish() 함수도 callback 으로 동작합니다.
// mp3 파일이 동작중에 play를 시키면 중간에 끊기고, didFinish() callback 도 동작하지 않습니다.
class AudioManager :NSObject {
    // MARK: - Public Variable
    public static let shared = AudioManager()
    var player: AVAudioPlayer?
    
    // MARK: - Private Variable
    private var playList:[AudioEnum] = []
    
    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, options: .mixWithOthers)
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            HHLog.e(.audio, .no0005, "error")
        }
    }
    
    // MARK: - Public Method
    // 실제 출력 함수(2)
    func play(audioEnum:AudioEnum, useQueue:Bool) {
        if isPlaying() {
            if useQueue == true {
                playList.append(audioEnum)
            } else {
                return
            }
        } else {
            playList.append(audioEnum)
            justPlay(audioEnum: audioEnum)
        }
    }
    
    func playCounting(counting:Int, useQueue:Bool) {
        switch counting {
        case 1: play(audioEnum: .count_01, useQueue: useQueue)
        case 2: play(audioEnum: .count_02, useQueue: useQueue)
        case 3: play(audioEnum: .count_03, useQueue: useQueue)
        case 4: play(audioEnum: .count_04, useQueue: useQueue)
        case 5: play(audioEnum: .count_05, useQueue: useQueue)
        case 6: play(audioEnum: .count_06, useQueue: useQueue)
        case 7: play(audioEnum: .count_07, useQueue: useQueue)
        case 8: play(audioEnum: .count_08, useQueue: useQueue)
        case 9: play(audioEnum: .count_09, useQueue: useQueue)
        case 10: play(audioEnum: .count_10, useQueue: useQueue)
        case 11: play(audioEnum: .count_11, useQueue: useQueue)
        case 12: play(audioEnum: .count_12, useQueue: useQueue)
        case 13: play(audioEnum: .count_13, useQueue: useQueue)
        case 14: play(audioEnum: .count_14, useQueue: useQueue)
        case 15: play(audioEnum: .count_15, useQueue: useQueue)
        case 16: play(audioEnum: .count_16, useQueue: useQueue)
        case 17: play(audioEnum: .count_17, useQueue: useQueue)
        case 18: play(audioEnum: .count_18, useQueue: useQueue)
        case 19: play(audioEnum: .count_19, useQueue: useQueue)
        case 20: play(audioEnum: .count_20, useQueue: useQueue)
        case 21: play(audioEnum: .count_21, useQueue: useQueue)
        case 22: play(audioEnum: .count_22, useQueue: useQueue)
        case 23: play(audioEnum: .count_23, useQueue: useQueue)
        case 24: play(audioEnum: .count_24, useQueue: useQueue)
        case 25: play(audioEnum: .count_25, useQueue: useQueue)
        case 26: play(audioEnum: .count_26, useQueue: useQueue)
        case 27: play(audioEnum: .count_27, useQueue: useQueue)
        case 28: play(audioEnum: .count_28, useQueue: useQueue)
        case 29: play(audioEnum: .count_29, useQueue: useQueue)
        case 30: play(audioEnum: .count_30, useQueue: useQueue)
        default: play(audioEnum: .count_30, useQueue: useQueue)
        }
    }
    
    func isPlaying() -> Bool {
        if let player = player {
            return player.isPlaying
        }
        return false
    }

    // MARK: - Private Method
    private func justPlay(audioEnum:AudioEnum) {
        do {
            let urlString  = Bundle.main.path(forResource: audioEnum.getFileName(), ofType: "mp3")
            guard let urlString = urlString else {
                HHLog.e(.audio, .no0006, "[ERROR] urlString is nil, audioEnum = \(audioEnum)")
                return
            }
            HHLog.d(.audio, "urlString = \(urlString)")
            player = try AVAudioPlayer(contentsOf: URL(string: urlString)!)
            
            guard let player = player else {
                HHLog.e(.audio, .no0007, "[ERROR] player is nil")
                return
            }
            HHLog.d(.audio, "duration = \(player.duration)")
            player.volume = 1.0
            player.delegate = self
            player.play()
            
        } catch {
            HHLog.e(.audio, .no0008, "play error")
        }
    }
}

// MARK: - Delegate
extension AudioManager : AVAudioPlayerDelegate {
    func audioPlayerDidFinishPlaying(_ player: AVAudioPlayer, successfully flag: Bool) {
        HHLog.d(.audio, "audioPlayerDidFinishPlaying()")
        playList.remove(at: 0)
        if playList.count > 0 {
            justPlay(audioEnum:playList[0])
        }
    }
    
    func audioPlayerDecodeErrorDidOccur(_ player: AVAudioPlayer, error: Error?) {
        HHLog.d(.audio, "audioPlayerDecodeErrorDidOccur()")
    }

    func audioPlayerBeginInterruption(_ player: AVAudioPlayer) {
        HHLog.d(.audio, "audioPlayerBeginInterruption()")
    }
    
    func audioPlayerEndInterruption(_ player: AVAudioPlayer, withOptions flags: Int) {
        HHLog.d(.audio, "audioPlayerEndInterruption()")
    }
}


