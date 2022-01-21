//
//  IOSAL.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/19.
//

import Foundation
import PoseEngine

class IOSAL:OSAL {
    func csvSaver(csvDataListFloat: NSMutableArray, exerciseName: String, nowSet: Int32, counting: Int32, repsAfter: Int32) {
        HHLog.d(.poseDetect, "csvSaver")
        //GlobalSubject.shared.csvSubject.onNext((filename, setNum))
    }
    
    func postFile(file: String, successCount: Int32, failCount: Int32, runningTime: Int64, restTime: Int64, isFreeMode: Bool) {
        HHLog.d(.poseDetect, "poseFile")
    }
    
    func resetRepCounter() {
        HHLog.d(.poseDetect, "resetRepCounter")
    }
    
    func elapsedRealtime() -> Int64 {
        return Int64(Date().timeIntervalSince1970 * 1000)
    }
        
    func logE(tag: String, log: String) {
        HHLog.e(.poseDetect,.no0100 ,"\(tag):\(log)")
    }
    
    func logW(tag: String, log: String) {
        HHLog.w(.poseDetect, "\(tag):\(log)")
    }
    
    func logV(tag: String, log: String) {
        HHLog.v(.poseDetect, "\(tag):\(log)")
    }
    
    func logD(tag: String, log: String) {
        HHLog.d(.poseDetect, "\(tag):\(log)")
    }

    func playCountSound(count: Int32, param: String) {
        HHLog.d(.poseDetect, "playCountSound() count = \(count), param = \(param)")
        AudioManager.shared.playCounting(counting: Int(count), useQueue: true)
        playSound(soundName:param)
    }
    
    func playOnlyCountSound(count: Int32) {
        HHLog.d(.poseDetect, "playOnlyCountSound() count = \(count)")
        AudioManager.shared.playCounting(counting: Int(count), useQueue: true)
    }
    
    func playSound(soundName: String) {
        HHLog.d(.poseDetect, "playSound() soundName = \(soundName)")
        var audioEnum:AudioEnum = AudioEnum.armsStraight
        switch soundName {
        case "start":
            audioEnum = .start
        case "end":
            audioEnum = .end
        case "already":
            audioEnum = .already
        case "no":
            audioEnum = .no
        case "lat_pull_down_3et_end":
            audioEnum = .latPullDown3SetEnd
        case "lunge_3set_end":
            audioEnum = .lunge3SetEnd
        case "squat_3set_end":
            audioEnum = .squat3SetEnd
        case "shoulder_press_3set_end":
            audioEnum = .shoulderPress3SetEnd
        case "side_lateral_raise_3set_end":
            audioEnum = .sideLateralRaise3SetEnd
            //녹화 시작, 종료
        case "recording_start":
            audioEnum = .recordingStart
        case "recording_end":
            audioEnum = .recordingEnd
            //휴식, 운동 진행세트
        case "rest":
            audioEnum = .rest
        case "set_first":
            audioEnum = .setFirst
        case "set_second":
            audioEnum = .setSecond
        case "set_third":
            audioEnum = .setThird
        case "rest_before_10":
            audioEnum = .restBefore10
        case "rest_before_3":
            audioEnum = .restBefore3

            
        case "rating_1":
            audioEnum = .rating1
        case "rating_2":
            audioEnum = .rating2
        case "rating_3":
            audioEnum = .rating3
        case "end_perfect":
            audioEnum = .endPerfect
            //공통적인 조언
        case "repeat":
            audioEnum = .repeat
        case "move":
            audioEnum = .move
        case "front":
            audioEnum = .front
        case "fast":
            audioEnum = .fast
        case "shrink":
            audioEnum = .shrink
        case "relaxation":
            audioEnum = .relaxation
            //운동 상세 조언
        case "up_to_head_dumbbell":
            audioEnum = .uptoHeadDumbbell
        case "head_dumbbell":
            audioEnum = .headDumbbell
        case "knees_toes":
            audioEnum = .kneesToes
        case "knee_degree_90":
            audioEnum = .kneeDegree90
        case "knee_foot_angle":
            audioEnum = .kneeFootAngle
        case "shoulder_over":
            audioEnum = .shoulderOver
        case "spread_shoulder":
            audioEnum = .spreadShoulder
        case "down_wrist":
            audioEnum = .downWrist
        case "up_wrist":
            audioEnum = .upWrist
        case "side_hands":
            audioEnum = .sideHands
        case "arms_straight":
            audioEnum = .armsStraight
        case "hip":
            audioEnum = .hip
        case "waist":
            audioEnum = .waist
        case "hip_vertical":
            audioEnum = .hipVertical
        case "foot_pelvis_distance":
            audioEnum = .footPelvisDistance
        case "up_elbow":
            audioEnum = .upElbow
        case "elbow_straight":
            audioEnum = .elbowStraight
        case "elbow_vertical":
            audioEnum = .elbowVertical
        case "long_bar":
            audioEnum = .longBar
        case "down_bar":
            audioEnum = .downBar
        case "fix_hip":
            audioEnum = .fixHip
        case "fix_elbow":
            audioEnum = .fixElbow
            
        case "foot_straight":
            audioEnum = .none
        case "알맞은 속도에요":
            audioEnum = .none
        case "속도가 빨라요":
            audioEnum = .none
        case "ending":
            audioEnum = .none
        case "perfect_1":
            audioEnum = .none
        case "perfect_2":
            audioEnum = .none
        case "perfect_3":
            audioEnum = .none
        case "perfect_4":
            audioEnum = .none
        case "good_1":
            audioEnum = .none
        case "good_2":
            audioEnum = .none
        case "good_3":
            audioEnum = .none
        case "good_4":
            audioEnum = .none
        case "soso_1":
            audioEnum = .none
        case "soso_2":
            audioEnum = .none
        case "soso_3":
            audioEnum = .none
        case "rating_4":
            audioEnum = .none
        default:
            audioEnum = .none
        }
        AudioManager.shared.play(audioEnum: audioEnum, useQueue: false)
    }
    
    func loadSampleFileOpener(runModel: String) -> String {
        var dataString: String? = nil
        do {
            dataString = try String(contentsOfFile: Bundle.main.path(forResource: runModel, ofType: "csv")!, encoding: .utf8)
        } catch {
            HHLog.e(.poseDetect, .no0100, "exerciseFile = \(runModel)")
        }
        return dataString!
    }
    
    func playLottieAnimation(animationId:Int32) {
        var lottieEnum:LottieEnum
        switch animationId {
        case 0:
            lottieEnum = .wait_10Sec
        case 1:
            lottieEnum = .congratulation
        default:
            lottieEnum = .none
        }
        GlobalSubject.shared.lottieSubject.onNext(lottieEnum)
    }
    
    func updateCount(count:Int32, totalCount:Int32) {
        GlobalSubject.shared.countSubject.onNext((Int(count), Int(totalCount)))
    }
}
