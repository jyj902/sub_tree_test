//
//  SkeletonView.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/17.
//

import UIKit
import MLKit

class SkeletonView: UIView {
    //var landmarks:[HHLandMark]?
    var iOSPose:IOSPose?
    var previewResolution:CGSize?
    var ratio:CGFloat = 0
    var isFront:Bool = false
    var exerciseType : Int = 0
    var isStart : Bool = false
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func initializeVariables() {
        if let previewResolution = previewResolution {
            ratio = CGFloat(bounds.height) / CGFloat(previewResolution.height)
        }
    }
    
    let circleSize:Double = 10
    override func draw(_ rect: CGRect) {
        guard let iOSPose = iOSPose else {
            return
        }
        let context = UIGraphicsGetCurrentContext()
        let colorBlue = UIColor.blue.cgColor
        let colorWhite = UIColor.white.cgColor
        let colorGreen = UIColor.green.cgColor
        let colorPuple = UIColor.purple.cgColor
        context?.setFillColor(colorWhite)
        if isStart {
            context?.setStrokeColor(colorGreen)
        } else {
            context?.setStrokeColor(colorBlue)
        }
        
        for (startLandmarkType, endLandmarkTypesArray) in poseConnections(iOSPose: iOSPose) {
            let translateStartLandmarkType = translatePoint(CGPoint(x:startLandmarkType.position.x, y:startLandmarkType.position.y))
            for endLandmarkType in endLandmarkTypesArray {
                let translateEndLandmarkType = translatePoint(CGPoint(x:endLandmarkType.position.x, y:endLandmarkType.position.y))
                if endLandmarkType.inFrameLikelihood > 0.85{
                    context?.setLineWidth(3.0)
                    context?.setLineCap(.round)
                    context?.move(to: CGPoint(x: translateStartLandmarkType.x , y: translateStartLandmarkType.y))
                    context?.addLine(to: CGPoint(x: translateEndLandmarkType.x, y: translateEndLandmarkType.y))
                    context?.strokePath()
                } else {
                    context?.setStrokeColor(colorPuple)
                    context?.setLineWidth(3.0)
                    context?.setLineCap(.round)
                    context?.move(to: CGPoint(x: translateStartLandmarkType.x , y: translateStartLandmarkType.y))
                    context?.addLine(to: CGPoint(x: translateEndLandmarkType.x, y: translateEndLandmarkType.y))
                    context?.strokePath()

                }
            }
//            let rectangle = CGRect(x: translateStartLandmarkType.x - circleSize/2 , y: translateStartLandmarkType.y - circleSize/2, width: circleSize, height: circleSize)
//            context?.addEllipse(in: rectangle)
//            context?.fillPath()
        }
        for point in posePoint(iOSPose: iOSPose){
            let translateData = translatePoint(CGPoint(x:point.x, y: point.y))
            let rectangle = CGRect(x: translateData.x - circleSize/2 , y: translateData.y - circleSize/2, width: circleSize, height: circleSize)
            context?.addEllipse(in: rectangle)
            context?.fillPath()
        }
    }
    
    func translatePoint(_ point:CGPoint) -> CGPoint {
        var translatedPoint:CGPoint = CGPoint()
        if isFront == false{
            if let previewResolution = previewResolution {
        //        translatedPoint.x = (point.x - 45) * 896/1920
        //        translatedPoint.y = point.y * 896/1920
                translatedPoint.x = CGFloat(bounds.width) - CGFloat(point.x) * ratio
                translatedPoint.y = CGFloat(point.y) * ratio
                return translatedPoint
            }
            return CGPoint(x:0, y:0)
        }
        else {
            if let previewResolution = previewResolution {
        //        translatedPoint.x = (point.x - 45) * 896/1920
        //        translatedPoint.y = point.y * 896/1920
                translatedPoint.x = CGFloat(point.x) * ratio
                translatedPoint.y = CGFloat(point.y) * ratio
                return translatedPoint
            }
            return CGPoint(x:0, y:0)
        }
    }
    func posePoint(iOSPose:IOSPose) -> [IOSPose.InData.Position] {
        // FULL, UPPER, LOWER
        switch exerciseType{
        case EXERCISE_INFORMATION.FULL :
            let point: [IOSPose.InData.Position] = [
                iOSPose.poseLandmark.leftShoulder.position,
                iOSPose.poseLandmark.rightShoulder.position,
                iOSPose.poseLandmark.leftHip.position,
                iOSPose.poseLandmark.rightHip.position,
                
                iOSPose.poseLandmark.rightElbow.position,
                iOSPose.poseLandmark.rightWrist.position,
                iOSPose.poseLandmark.leftElbow.position,
                iOSPose.poseLandmark.leftWrist.position,
                
                iOSPose.poseLandmark.rightKnee.position,
                iOSPose.poseLandmark.rightAnkle.position,
                iOSPose.poseLandmark.leftKnee.position,
                iOSPose.poseLandmark.leftAnkle.position,

            ]
            return point
        case EXERCISE_INFORMATION.UPPER :
            let point: [IOSPose.InData.Position] = [
                iOSPose.poseLandmark.leftShoulder.position,
                iOSPose.poseLandmark.rightShoulder.position,
                iOSPose.poseLandmark.leftHip.position,
                iOSPose.poseLandmark.rightHip.position,
                
                iOSPose.poseLandmark.rightElbow.position,
                iOSPose.poseLandmark.rightWrist.position,
                iOSPose.poseLandmark.leftElbow.position,
                iOSPose.poseLandmark.leftWrist.position,

            ]
            return point

        case EXERCISE_INFORMATION.LOWER :
            let point: [IOSPose.InData.Position] = [
                iOSPose.poseLandmark.leftShoulder.position,
                iOSPose.poseLandmark.rightShoulder.position,
                iOSPose.poseLandmark.leftHip.position,
                iOSPose.poseLandmark.rightHip.position,
                
                iOSPose.poseLandmark.rightKnee.position,
                iOSPose.poseLandmark.rightAnkle.position,
                iOSPose.poseLandmark.leftKnee.position,
                iOSPose.poseLandmark.leftAnkle.position,

            ]
            return point

        default:
            let point: [IOSPose.InData.Position] = [
                iOSPose.poseLandmark.leftShoulder.position,
                iOSPose.poseLandmark.rightShoulder.position,
                iOSPose.poseLandmark.leftHip.position,
                iOSPose.poseLandmark.rightHip.position,
                
                iOSPose.poseLandmark.rightElbow.position,
                iOSPose.poseLandmark.rightWrist.position,
                iOSPose.poseLandmark.leftElbow.position,
                iOSPose.poseLandmark.leftWrist.position,
                
                iOSPose.poseLandmark.rightKnee.position,
                iOSPose.poseLandmark.rightAnkle.position,
                iOSPose.poseLandmark.leftKnee.position,
                iOSPose.poseLandmark.leftAnkle.position,

            ]
            return point
        }
    }
    func poseConnections(iOSPose:IOSPose) -> [IOSPose.InData:[IOSPose.InData]] {
        // FULL, UPPER, LOWER
        switch exerciseType{
        case EXERCISE_INFORMATION.FULL :
            let connections: [IOSPose.InData: [IOSPose.InData]] = [
                iOSPose.poseLandmark.leftShoulder: [
                    iOSPose.poseLandmark.rightShoulder,
                    iOSPose.poseLandmark.leftHip,
                ],
                iOSPose.poseLandmark.rightShoulder: [
                    iOSPose.poseLandmark.rightHip,
                    iOSPose.poseLandmark.rightElbow,
                ],
                iOSPose.poseLandmark.rightWrist: [
                    iOSPose.poseLandmark.rightElbow,
                    iOSPose.poseLandmark.rightThumb,
                    iOSPose.poseLandmark.rightIndex,
                    iOSPose.poseLandmark.rightPinky,
                ],
                iOSPose.poseLandmark.leftHip: [iOSPose.poseLandmark.rightHip, iOSPose.poseLandmark.leftKnee],
                iOSPose.poseLandmark.rightHip: [iOSPose.poseLandmark.rightKnee],
                iOSPose.poseLandmark.rightKnee: [iOSPose.poseLandmark.rightAnkle],
                iOSPose.poseLandmark.leftKnee: [iOSPose.poseLandmark.leftAnkle],
                iOSPose.poseLandmark.leftElbow: [iOSPose.poseLandmark.leftShoulder],
                iOSPose.poseLandmark.leftWrist: [
                    iOSPose.poseLandmark.leftElbow, iOSPose.poseLandmark.leftThumb,
                    iOSPose.poseLandmark.leftIndex,
                    iOSPose.poseLandmark.leftPinky,
                ],
                iOSPose.poseLandmark.leftAnkle: [iOSPose.poseLandmark.leftHeel, iOSPose.poseLandmark.leftFootIndex],
                iOSPose.poseLandmark.rightAnkle: [iOSPose.poseLandmark.rightHeel, iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.rightHeel: [iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.leftHeel: [iOSPose.poseLandmark.leftFootIndex],
                iOSPose.poseLandmark.rightIndex: [iOSPose.poseLandmark.rightPinky],
                iOSPose.poseLandmark.leftIndex: [iOSPose.poseLandmark.leftPinky]
            ]
            return connections
        case EXERCISE_INFORMATION.UPPER :
            let connections: [IOSPose.InData: [IOSPose.InData]] = [
                iOSPose.poseLandmark.leftShoulder: [
                    iOSPose.poseLandmark.rightShoulder,
                    iOSPose.poseLandmark.leftHip,
                    iOSPose.poseLandmark.leftElbow
                ],
                iOSPose.poseLandmark.rightShoulder: [
                    iOSPose.poseLandmark.rightHip,
                    iOSPose.poseLandmark.rightElbow,

                ],
                iOSPose.poseLandmark.leftHip: [
                    iOSPose.poseLandmark.rightHip
                ],
                iOSPose.poseLandmark.rightWrist: [
                    iOSPose.poseLandmark.rightElbow
                ],
                iOSPose.poseLandmark.leftWrist: [
                    iOSPose.poseLandmark.leftElbow
                ],
            ]
            return connections
        case EXERCISE_INFORMATION.LOWER :
            let connections: [IOSPose.InData: [IOSPose.InData]] = [
                iOSPose.poseLandmark.leftShoulder: [
                    iOSPose.poseLandmark.rightShoulder,
                    iOSPose.poseLandmark.leftHip,
                ],
                iOSPose.poseLandmark.rightShoulder: [
                    iOSPose.poseLandmark.rightHip,
                ],
                iOSPose.poseLandmark.leftHip: [iOSPose.poseLandmark.rightHip, iOSPose.poseLandmark.leftKnee],
                iOSPose.poseLandmark.rightHip: [iOSPose.poseLandmark.rightKnee],
                iOSPose.poseLandmark.rightKnee: [iOSPose.poseLandmark.rightAnkle],
                iOSPose.poseLandmark.leftKnee: [iOSPose.poseLandmark.leftAnkle],

                iOSPose.poseLandmark.leftAnkle: [iOSPose.poseLandmark.leftHeel, iOSPose.poseLandmark.leftFootIndex],
                iOSPose.poseLandmark.rightAnkle: [iOSPose.poseLandmark.rightHeel, iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.rightHeel: [iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.leftHeel: [iOSPose.poseLandmark.leftFootIndex],

            ]
            return connections
        default:
            let connections: [IOSPose.InData: [IOSPose.InData]] = [
                //face
    //            iOSPose.poseLandmark.leftEar.position: [iOSPose.poseLandmark.leftEyeOuter.position],
    //            iOSPose.poseLandmark.leftEyeOuter.position: [iOSPose.poseLandmark.leftEye.position],
    //            iOSPose.poseLandmark.leftEye.position: [iOSPose.poseLandmark.leftEyeInner.position],
    //            iOSPose.poseLandmark.leftEyeInner.position: [iOSPose.poseLandmark.nose.position],
    //            iOSPose.poseLandmark.nose.position: [iOSPose.poseLandmark.rightEyeInner.position],
    //            iOSPose.poseLandmark.rightEyeInner.position: [iOSPose.poseLandmark.rightEye.position],
    //            iOSPose.poseLandmark.rightEye.position: [iOSPose.poseLandmark.rightEyeOuter.position],
    //            iOSPose.poseLandmark.rightEyeOuter.position: [iOSPose.poseLandmark.rightEar.position],
    //            iOSPose.poseLandmark.leftMouth.position: [iOSPose.poseLandmark.rightMouth.position],
                iOSPose.poseLandmark.leftShoulder: [
                    iOSPose.poseLandmark.rightShoulder,
                    iOSPose.poseLandmark.leftHip,
                ],
                iOSPose.poseLandmark.rightShoulder: [
                    iOSPose.poseLandmark.rightHip,
                    iOSPose.poseLandmark.rightElbow,
                ],
                iOSPose.poseLandmark.rightWrist: [
                    iOSPose.poseLandmark.rightElbow,
                    iOSPose.poseLandmark.rightThumb,
                    iOSPose.poseLandmark.rightIndex,
                    iOSPose.poseLandmark.rightPinky,
                ],
                iOSPose.poseLandmark.leftHip: [iOSPose.poseLandmark.rightHip, iOSPose.poseLandmark.leftKnee],
                iOSPose.poseLandmark.rightHip: [iOSPose.poseLandmark.rightKnee],
                iOSPose.poseLandmark.rightKnee: [iOSPose.poseLandmark.rightAnkle],
                iOSPose.poseLandmark.leftKnee: [iOSPose.poseLandmark.leftAnkle],
                iOSPose.poseLandmark.leftElbow: [iOSPose.poseLandmark.leftShoulder],
                iOSPose.poseLandmark.leftWrist: [
                    iOSPose.poseLandmark.leftElbow, iOSPose.poseLandmark.leftThumb,
                    iOSPose.poseLandmark.leftIndex,
                    iOSPose.poseLandmark.leftPinky,
                ],
                iOSPose.poseLandmark.leftAnkle: [iOSPose.poseLandmark.leftHeel, iOSPose.poseLandmark.leftFootIndex],
                iOSPose.poseLandmark.rightAnkle: [iOSPose.poseLandmark.rightHeel, iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.rightHeel: [iOSPose.poseLandmark.rightFootIndex],
                iOSPose.poseLandmark.leftHeel: [iOSPose.poseLandmark.leftFootIndex],
                iOSPose.poseLandmark.rightIndex: [iOSPose.poseLandmark.rightPinky],
                iOSPose.poseLandmark.leftIndex: [iOSPose.poseLandmark.leftPinky]
            ]
            return connections
        }
    }
}
