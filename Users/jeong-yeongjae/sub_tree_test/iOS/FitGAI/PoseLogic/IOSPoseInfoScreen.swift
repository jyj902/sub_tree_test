//
//  IOSPoseInfoScreen.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/19.
//

import Foundation
import PoseEngine

class IOSPoseInfoScreen :PoseInfoScreen {
    func finishExercise(isFinish: Bool) {
        HHLog.d(.poseDetect, "finishExercise()")
    }

    func postFile(file: String, successCount: Int32, failCount: Int32, runningTime: Int64, restTime: Int64, isFreeMode: Bool) {
        HHLog.d(.poseDetect, "postFile()")
    }
    
    func setCount(count: Int32) {
        HHLog.d(.poseDetect, "setCount()")
    }
    
    func setIntfo(setData: KotlinPair<NSString, KotlinBoolean>) {
        HHLog.d(.poseDetect, "setIntfo()")
    }
    
    func showRestTime10() {
        HHLog.d(.poseDetect, "showRestTime10()")
    }
    
    func testInfoResult(exercisePose: ExercisePose) {
        HHLog.d(.poseDetect, "testInfoResult()")
    }
    
    func userInfoResult(exercisePose: ExercisePose) {
        HHLog.d(.poseDetect, "userInfoResult()")
    }
}
