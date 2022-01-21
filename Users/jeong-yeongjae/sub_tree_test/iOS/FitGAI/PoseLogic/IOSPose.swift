//
//  iOSPose.swift
//  FitGAI
//
//  Created by 정영재 on 2021/12/17.
//

import Foundation
import MLKit

class IOSPose{
    var pose : Pose?
    var poseLandmark : PoseLandmark
    init(){
        self.pose = nil
        self.poseLandmark = PoseLandmark()
    }
    init(_ pose:Pose){
        self.pose = pose
        self.poseLandmark = PoseLandmark()
        self.poseLandmark.nose.position.x = pose.landmark(ofType: .nose).position.y
        self.poseLandmark.nose.position.y = pose.landmark(ofType: .nose).position.x
        self.poseLandmark.nose.position.z = pose.landmark(ofType: .nose).position.z
        self.poseLandmark.nose.inFrameLikelihood = pose.landmark(ofType: .nose).inFrameLikelihood
        
        self.poseLandmark.leftEyeInner.position.x = pose.landmark(ofType: .leftEyeInner).position.y
        self.poseLandmark.leftEyeInner.position.y = pose.landmark(ofType: .leftEyeInner).position.x
        self.poseLandmark.leftEyeInner.position.z = pose.landmark(ofType: .leftEyeInner).position.z
        self.poseLandmark.leftEyeInner.inFrameLikelihood = pose.landmark(ofType: .leftEyeInner).inFrameLikelihood
        
        self.poseLandmark.leftEye.position.x = pose.landmark(ofType: .leftEye).position.y
        self.poseLandmark.leftEye.position.y = pose.landmark(ofType: .leftEye).position.x
        self.poseLandmark.leftEye.position.z = pose.landmark(ofType: .leftEye).position.z
        self.poseLandmark.leftEye.inFrameLikelihood = pose.landmark(ofType: .leftEye).inFrameLikelihood
        
        self.poseLandmark.leftEyeOuter.position.x = pose.landmark(ofType: .leftEyeOuter).position.y
        self.poseLandmark.leftEyeOuter.position.y = pose.landmark(ofType: .leftEyeOuter).position.x
        self.poseLandmark.leftEyeOuter.position.z = pose.landmark(ofType: .leftEyeOuter).position.z
        self.poseLandmark.leftEyeOuter.inFrameLikelihood = pose.landmark(ofType: .leftEyeOuter).inFrameLikelihood
        
        self.poseLandmark.rightEyeInner.position.x = pose.landmark(ofType: .rightEyeInner).position.y
        self.poseLandmark.rightEyeInner.position.y = pose.landmark(ofType: .rightEyeInner).position.x
        self.poseLandmark.rightEyeInner.position.z = pose.landmark(ofType: .rightEyeInner).position.z
        self.poseLandmark.rightEyeInner.inFrameLikelihood = pose.landmark(ofType: .rightEyeInner).inFrameLikelihood
        
        self.poseLandmark.rightEye.position.x = pose.landmark(ofType: .rightEye).position.y
        self.poseLandmark.rightEye.position.y = pose.landmark(ofType: .rightEye).position.x
        self.poseLandmark.rightEye.position.z = pose.landmark(ofType: .rightEye).position.z
        self.poseLandmark.rightEye.inFrameLikelihood = pose.landmark(ofType: .rightEye).inFrameLikelihood
        
        self.poseLandmark.rightEyeOuter.position.x = pose.landmark(ofType: .rightEyeOuter).position.y
        self.poseLandmark.rightEyeOuter.position.y = pose.landmark(ofType: .rightEyeOuter).position.x
        self.poseLandmark.rightEyeOuter.position.z = pose.landmark(ofType: .rightEyeOuter).position.z
        self.poseLandmark.rightEyeOuter.inFrameLikelihood = pose.landmark(ofType: .rightEyeOuter).inFrameLikelihood
        
        self.poseLandmark.leftEar.position.x = pose.landmark(ofType: .leftEar).position.y
        self.poseLandmark.leftEar.position.y = pose.landmark(ofType: .leftEar).position.x
        self.poseLandmark.leftEar.position.z = pose.landmark(ofType: .leftEar).position.z
        self.poseLandmark.leftEar.inFrameLikelihood = pose.landmark(ofType: .leftEar).inFrameLikelihood
        
        self.poseLandmark.rightEar.position.x = pose.landmark(ofType: .rightEar).position.y
        self.poseLandmark.rightEar.position.y = pose.landmark(ofType: .rightEar).position.x
        self.poseLandmark.rightEar.position.z = pose.landmark(ofType: .rightEar).position.z
        self.poseLandmark.rightEar.inFrameLikelihood = pose.landmark(ofType: .rightEar).inFrameLikelihood
        
        self.poseLandmark.leftMouth.position.x = pose.landmark(ofType: .mouthLeft).position.y
        self.poseLandmark.leftMouth.position.y = pose.landmark(ofType: .mouthLeft).position.x
        self.poseLandmark.leftMouth.position.z = pose.landmark(ofType: .mouthLeft).position.z
        self.poseLandmark.leftMouth.inFrameLikelihood = pose.landmark(ofType: .mouthLeft).inFrameLikelihood
        
        self.poseLandmark.rightMouth.position.x = pose.landmark(ofType: .mouthRight).position.y
        self.poseLandmark.rightMouth.position.y = pose.landmark(ofType: .mouthRight).position.x
        self.poseLandmark.rightMouth.position.z = pose.landmark(ofType: .mouthRight).position.z
        self.poseLandmark.rightMouth.inFrameLikelihood = pose.landmark(ofType: .mouthRight).inFrameLikelihood
        
        self.poseLandmark.leftShoulder.position.x = pose.landmark(ofType: .leftShoulder).position.y
        self.poseLandmark.leftShoulder.position.y = pose.landmark(ofType: .leftShoulder).position.x
        self.poseLandmark.leftShoulder.position.z = pose.landmark(ofType: .leftShoulder).position.z
        self.poseLandmark.leftShoulder.inFrameLikelihood = pose.landmark(ofType: .leftShoulder).inFrameLikelihood
        
        self.poseLandmark.rightShoulder.position.x = pose.landmark(ofType: .rightShoulder).position.y
        self.poseLandmark.rightShoulder.position.y = pose.landmark(ofType: .rightShoulder).position.x
        self.poseLandmark.rightShoulder.position.z = pose.landmark(ofType: .rightShoulder).position.z
        self.poseLandmark.rightShoulder.inFrameLikelihood = pose.landmark(ofType: .rightShoulder).inFrameLikelihood
        
        self.poseLandmark.leftElbow.position.x = pose.landmark(ofType: .leftElbow).position.y
        self.poseLandmark.leftElbow.position.y = pose.landmark(ofType: .leftElbow).position.x
        self.poseLandmark.leftElbow.position.z = pose.landmark(ofType: .leftElbow).position.z
        self.poseLandmark.leftElbow.inFrameLikelihood = pose.landmark(ofType: .leftElbow).inFrameLikelihood
        
        self.poseLandmark.rightElbow.position.x = pose.landmark(ofType: .rightElbow).position.y
        self.poseLandmark.rightElbow.position.y = pose.landmark(ofType: .rightElbow).position.x
        self.poseLandmark.rightElbow.position.z = pose.landmark(ofType: .rightElbow).position.z
        self.poseLandmark.rightElbow.inFrameLikelihood = pose.landmark(ofType: .rightElbow).inFrameLikelihood
        
        self.poseLandmark.leftWrist.position.x = pose.landmark(ofType: .leftWrist).position.y
        self.poseLandmark.leftWrist.position.y = pose.landmark(ofType: .leftWrist).position.x
        self.poseLandmark.leftWrist.position.z = pose.landmark(ofType: .leftWrist).position.z
        self.poseLandmark.leftWrist.inFrameLikelihood = pose.landmark(ofType: .leftWrist).inFrameLikelihood
        
        self.poseLandmark.rightWrist.position.x = pose.landmark(ofType: .rightWrist).position.y
        self.poseLandmark.rightWrist.position.y = pose.landmark(ofType: .rightWrist).position.x
        self.poseLandmark.rightWrist.position.z = pose.landmark(ofType: .rightWrist).position.z
        self.poseLandmark.rightWrist.inFrameLikelihood = pose.landmark(ofType: .rightWrist).inFrameLikelihood
        
        self.poseLandmark.leftPinky.position.x = pose.landmark(ofType: .leftPinkyFinger).position.y
        self.poseLandmark.leftPinky.position.y = pose.landmark(ofType: .leftPinkyFinger).position.x
        self.poseLandmark.leftPinky.position.z = pose.landmark(ofType: .leftPinkyFinger).position.z
        self.poseLandmark.leftPinky.inFrameLikelihood = pose.landmark(ofType: .leftPinkyFinger).inFrameLikelihood
        
        self.poseLandmark.rightPinky.position.x = pose.landmark(ofType: .rightPinkyFinger).position.y
        self.poseLandmark.rightPinky.position.y = pose.landmark(ofType: .rightPinkyFinger).position.x
        self.poseLandmark.rightPinky.position.z = pose.landmark(ofType: .rightPinkyFinger).position.z
        self.poseLandmark.rightPinky.inFrameLikelihood = pose.landmark(ofType: .rightPinkyFinger).inFrameLikelihood
        
        self.poseLandmark.leftIndex.position.x = pose.landmark(ofType: .leftIndexFinger).position.y
        self.poseLandmark.leftIndex.position.y = pose.landmark(ofType: .leftIndexFinger).position.x
        self.poseLandmark.leftIndex.position.z = pose.landmark(ofType: .leftIndexFinger).position.z
        self.poseLandmark.leftIndex.inFrameLikelihood = pose.landmark(ofType: .leftIndexFinger).inFrameLikelihood
        
        self.poseLandmark.nose.position.x = pose.landmark(ofType: .nose).position.y
        self.poseLandmark.nose.position.y = pose.landmark(ofType: .nose).position.x
        self.poseLandmark.nose.position.z = pose.landmark(ofType: .nose).position.z
        self.poseLandmark.nose.inFrameLikelihood = pose.landmark(ofType: .nose).inFrameLikelihood
        
        self.poseLandmark.rightIndex.position.x = pose.landmark(ofType: .rightIndexFinger).position.y
        self.poseLandmark.rightIndex.position.y = pose.landmark(ofType: .rightIndexFinger).position.x
        self.poseLandmark.rightIndex.position.z = pose.landmark(ofType: .rightIndexFinger).position.z
        self.poseLandmark.rightIndex.inFrameLikelihood = pose.landmark(ofType: .rightIndexFinger).inFrameLikelihood
        
        self.poseLandmark.leftThumb.position.x = pose.landmark(ofType: .leftThumb).position.y
        self.poseLandmark.leftThumb.position.y = pose.landmark(ofType: .leftThumb).position.x
        self.poseLandmark.leftThumb.position.z = pose.landmark(ofType: .leftThumb).position.z
        self.poseLandmark.leftThumb.inFrameLikelihood = pose.landmark(ofType: .leftThumb).inFrameLikelihood
        
        self.poseLandmark.rightThumb.position.x = pose.landmark(ofType: .rightThumb).position.y
        self.poseLandmark.rightThumb.position.y = pose.landmark(ofType: .rightThumb).position.x
        self.poseLandmark.rightThumb.position.z = pose.landmark(ofType: .rightThumb).position.z
        self.poseLandmark.rightThumb.inFrameLikelihood = pose.landmark(ofType: .rightThumb).inFrameLikelihood
        
        self.poseLandmark.leftHip.position.x = pose.landmark(ofType: .leftHip).position.y
        self.poseLandmark.leftHip.position.y = pose.landmark(ofType: .leftHip).position.x
        self.poseLandmark.leftHip.position.z = pose.landmark(ofType: .leftHip).position.z
        self.poseLandmark.leftHip.inFrameLikelihood = pose.landmark(ofType: .nose).inFrameLikelihood
        
        self.poseLandmark.rightHip.position.x = pose.landmark(ofType: .rightHip).position.y
        self.poseLandmark.rightHip.position.y = pose.landmark(ofType: .rightHip).position.x
        self.poseLandmark.rightHip.position.z = pose.landmark(ofType: .rightHip).position.z
        self.poseLandmark.rightHip.inFrameLikelihood = pose.landmark(ofType: .rightHip).inFrameLikelihood
        
        self.poseLandmark.leftKnee.position.x = pose.landmark(ofType: .leftKnee).position.y
        self.poseLandmark.leftKnee.position.y = pose.landmark(ofType: .leftKnee).position.x
        self.poseLandmark.leftKnee.position.z = pose.landmark(ofType: .leftKnee).position.z
        self.poseLandmark.leftKnee.inFrameLikelihood = pose.landmark(ofType: .leftKnee).inFrameLikelihood
        
        self.poseLandmark.rightKnee.position.x = pose.landmark(ofType: .rightKnee).position.y
        self.poseLandmark.rightKnee.position.y = pose.landmark(ofType: .rightKnee).position.x
        self.poseLandmark.rightKnee.position.z = pose.landmark(ofType: .rightKnee).position.z
        self.poseLandmark.rightKnee.inFrameLikelihood = pose.landmark(ofType: .rightKnee).inFrameLikelihood
        
        self.poseLandmark.leftAnkle.position.x = pose.landmark(ofType: .leftAnkle).position.y
        self.poseLandmark.leftAnkle.position.y = pose.landmark(ofType: .leftAnkle).position.x
        self.poseLandmark.leftAnkle.position.z = pose.landmark(ofType: .leftAnkle).position.z
        self.poseLandmark.leftAnkle.inFrameLikelihood = pose.landmark(ofType: .leftAnkle).inFrameLikelihood
        
        self.poseLandmark.rightAnkle.position.x = pose.landmark(ofType: .rightAnkle).position.y
        self.poseLandmark.rightAnkle.position.y = pose.landmark(ofType: .rightAnkle).position.x
        self.poseLandmark.rightAnkle.position.z = pose.landmark(ofType: .rightAnkle).position.z
        self.poseLandmark.rightAnkle.inFrameLikelihood = pose.landmark(ofType: .rightAnkle).inFrameLikelihood
        
        self.poseLandmark.leftHeel.position.x = pose.landmark(ofType: .leftHeel).position.y
        self.poseLandmark.leftHeel.position.y = pose.landmark(ofType: .leftHeel).position.x
        self.poseLandmark.leftHeel.position.z = pose.landmark(ofType: .leftHeel).position.z
        self.poseLandmark.leftHeel.inFrameLikelihood = pose.landmark(ofType: .leftHeel).inFrameLikelihood
        
        self.poseLandmark.rightHeel.position.x = pose.landmark(ofType: .rightHeel).position.y
        self.poseLandmark.rightHeel.position.y = pose.landmark(ofType: .rightHeel).position.x
        self.poseLandmark.rightHeel.position.z = pose.landmark(ofType: .rightHeel).position.z
        self.poseLandmark.rightHeel.inFrameLikelihood = pose.landmark(ofType: .rightHeel).inFrameLikelihood
        
        self.poseLandmark.leftFootIndex.position.x = pose.landmark(ofType: .leftToe).position.y
        self.poseLandmark.leftFootIndex.position.y = pose.landmark(ofType: .leftToe).position.x
        self.poseLandmark.leftFootIndex.position.z = pose.landmark(ofType: .leftToe).position.z
        self.poseLandmark.leftFootIndex.inFrameLikelihood = pose.landmark(ofType: .leftToe).inFrameLikelihood
        
        self.poseLandmark.rightFootIndex.position.x = pose.landmark(ofType: .rightToe).position.y
        self.poseLandmark.rightFootIndex.position.y = pose.landmark(ofType: .rightToe).position.x
        self.poseLandmark.rightFootIndex.position.z = pose.landmark(ofType: .rightToe).position.z
        self.poseLandmark.rightFootIndex.inFrameLikelihood = pose.landmark(ofType: .rightToe).inFrameLikelihood
        
        
    }
    
    struct InData : Hashable {
        struct Position : Hashable {
            var x : Double
            var y : Double
            var z : Double
            init(x:Double, y:Double, z:Double){
                self.x = x
                self.y = y
                self.z = z
            }
            init(){
                self.x = 0.0
                self.y = 0.0
                self.z = 0.0
            }
        }
        var inFrameLikelihood : Float
        var position : Position
        init( position:Position ,_ inFrameLikelihood:Float) {
            self.position = position
            self.inFrameLikelihood = inFrameLikelihood
        }
        init(){
            position = Position()
            inFrameLikelihood = 0.0
        }
    }
    
    struct PoseLandmark{
        var nose            : InData
        var leftEyeInner    : InData
        var leftEye         : InData
        var leftEyeOuter    : InData
        var rightEyeInner   : InData
        var rightEye        : InData
        var rightEyeOuter   : InData
        var leftEar         : InData
        var rightEar        : InData
        var leftMouth       : InData
        var rightMouth      : InData
        var leftShoulder    : InData
        var rightShoulder   : InData
        var leftElbow       : InData
        var rightElbow      : InData
        var leftWrist       : InData
        var rightWrist      : InData
        var leftPinky       : InData
        var rightPinky      : InData
        var leftIndex       : InData
        var rightIndex      : InData
        var leftThumb       : InData
        var rightThumb      : InData
        var leftHip         : InData
        var rightHip        : InData
        var leftKnee        : InData
        var rightKnee       : InData
        var leftAnkle       : InData
        var rightAnkle      : InData
        var leftHeel        : InData
        var rightHeel       : InData
        var leftFootIndex   : InData
        var rightFootIndex  : InData
        func landmark(_ type : Int) -> InData{
            if type == 0{
                return nose
            }
            if type == 1{
                return leftEyeInner
            }

            if type == 2{
                return leftEye
            }

            if type == 3{
                return leftEyeOuter
            }
            if type == 4{
                return rightEyeInner
            }
            if type == 5{
                return rightEye
            }
            if type == 6{
                return rightEyeOuter
            }
            if type == 7{
                return leftEar
            }
            if type == 8{
                return rightEar
            }
            if type == 9{
                return leftMouth
            }
            if type == 10{
                return rightMouth
            }
            if type == 11{
                return leftShoulder
            }
            if type == 12{
                return rightShoulder
            }
            if type == 13{
                return leftElbow
            }
            if type == 14{
                return rightElbow
            }
            if type == 15{
                return leftWrist
            }
            if type == 16{
                return rightWrist
            }
            if type == 17{
                return leftPinky
            }
            if type == 18{
                return rightPinky
            }
            if type == 19{
                return leftIndex
            }
            if type == 20{
                return rightIndex
            }
            if type == 21{
                return leftThumb
            }
            if type == 22{
                return rightThumb
            }
            if type == 23{
                return leftHip
            }
            if type == 24{
                return rightHip
            }
            if type == 25{
                return leftKnee
            }
            if type == 26{
                return rightKnee
            }
            if type == 27{
                return leftAnkle
            }
            if type == 28{
                return rightAnkle
            }
            if type == 29{
                return leftHeel
            }
            if type == 30{
                return rightHeel
            }
            if type == 31{
                return leftFootIndex
            }
            if type == 32{
                return rightFootIndex
            }
            return nose
        }
        
        init(){
            self.nose           = InData()
            self.leftEyeInner   = InData()
            self.leftEye        = InData()
            self.leftEyeOuter   = InData()
            self.rightEyeInner  = InData()
            self.rightEye       = InData()
            self.rightEyeOuter  = InData()
            self.leftEar        = InData()
            self.rightEar       = InData()
            self.leftMouth      = InData()
            self.rightMouth     = InData()
            self.leftShoulder   = InData()
            self.rightShoulder  = InData()
            self.leftElbow      = InData()
            self.rightElbow     = InData()
            self.leftWrist      = InData()
            self.rightWrist     = InData()
            self.leftPinky      = InData()
            self.rightPinky     = InData()
            self.leftIndex      = InData()
            self.rightIndex     = InData()
            self.leftThumb      = InData()
            self.rightThumb     = InData()
            self.leftHip        = InData()
            self.rightHip       = InData()
            self.leftKnee       = InData()
            self.rightKnee      = InData()
            self.leftAnkle      = InData()
            self.rightAnkle     = InData()
            self.leftHeel       = InData()
            self.rightHeel      = InData()
            self.leftFootIndex  = InData()
            self.rightFootIndex = InData()
        }
    }
}
