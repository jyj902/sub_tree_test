//
//  SkeletonView.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/17.
//

import UIKit


struct DebugLandMark {
    var type:DebugLandMarkType
    var x:Double
    var y:Double
    var z:Double
}


/*
 MLKPoseLandmarkType(_rawValue: Nose) = (x: 106.3996734619141, y: 185.3828887939453, z: -229.8639984130859)
 MLKPoseLandmarkType(_rawValue: MouthLeft) = (x: 115.4542922973633, y: 178.8846588134766, z: -205.7475128173828)
 MLKPoseLandmarkType(_rawValue: RightAnkle) = (x: 348.7644653320312, y: 213.649169921875, z: 202.7643737792969)
 MLKPoseLandmarkType(_rawValue: RightKnee) = (x: 299.7515869140625, y: 208.8989105224609, z: 46.43084716796875)
 MLKPoseLandmarkType(_rawValue: LeftKnee) = (x: 298.0560607910156, y: 163.3784637451172, z: 21.2111644744873)
 MLKPoseLandmarkType(_rawValue: RightWrist) = (x: 240.494384765625, y: 236.8681640625, z: -111.2972030639648)
 MLKPoseLandmarkType(_rawValue: LeftEyeOuter) = (x: 100.9446716308594, y: 175.0484466552734, z: -221.0108642578125)
 MLKPoseLandmarkType(_rawValue: LeftElbow) = (x: 200.4297637939453, y: 139.8223724365234, z: -85.78042602539062)
 MLKPoseLandmarkType(_rawValue: LeftHeel) = (x: 355.5595703125, y: 163.6918029785156, z: 183.832275390625)
 MLKPoseLandmarkType(_rawValue: RightElbow) = (x: 201.9906005859375, y: 227.9167633056641, z: -73.82023620605469)
 MLKPoseLandmarkType(_rawValue: LeftAnkle) = (x: 350.1436157226562, y: 160.1427307128906, z: 172.5124359130859)
 MLKPoseLandmarkType(_rawValue: RightIndexFinger) = (x: 255.4426879882812, y: 238.9244689941406, z: -145.9014587402344)
 MLKPoseLandmarkType(_rawValue: LeftEar) = (x: 105.3301467895508, y: 171.0313415527344, z: -158.8118133544922)
 MLKPoseLandmarkType(_rawValue: LeftEye) = (x: 100.686279296875, y: 178.0907135009766, z: -221.0595703125)
 MLKPoseLandmarkType(_rawValue: LeftPinkyFinger) = (x: 254.5600280761719, y: 125.0765075683594, z: -136.8862457275391)
 MLKPoseLandmarkType(_rawValue: LeftEyeInner) = (x: 100.5455627441406, y: 181.18115234375, z: -221.0390472412109)
 MLKPoseLandmarkType(_rawValue: RightEar) = (x: 107.5618896484375, y: 198.3082427978516, z: -155.3134307861328)
 MLKPoseLandmarkType(_rawValue: RightEyeOuter) = (x: 101.8272705078125, y: 195.16357421875, z: -220.2978515625)
 MLKPoseLandmarkType(_rawValue: RightEyeInner) = (x: 100.9715347290039, y: 189.8721008300781, z: -220.3697509765625)
 MLKPoseLandmarkType(_rawValue: RightThumb) = (x: 250.1357421875, y: 235.5218963623047, z: -120.390510559082)
 MLKPoseLandmarkType(_rawValue: LeftIndexFinger) = (x: 255.6887359619141, y: 127.5059967041016, z: -155.846923828125)
 MLKPoseLandmarkType(_rawValue: LeftThumb) = (x: 252.0674743652344, y: 130.4942474365234, z: -130.8651123046875)
 MLKPoseLandmarkType(_rawValue: RightHip) = (x: 236.4964294433594, y: 202.3072814941406, z: 2.907700061798096)
 MLKPoseLandmarkType(_rawValue: RightPinkyFinger) = (x: 253.9851379394531, y: 241.6973266601562, z: -126.6543045043945)
 MLKPoseLandmarkType(_rawValue: LeftToe) = (x: 375.07080078125, y: 153.5996551513672, z: 117.4762496948242)
 MLKPoseLandmarkType(_rawValue: RightToe) = (x: 372.5689086914062, y: 224.1183929443359, z: 150.9266204833984)
 MLKPoseLandmarkType(_rawValue: LeftHip) = (x: 236.3049163818359, y: 166.5417022705078, z: -2.930918455123901)
 MLKPoseLandmarkType(_rawValue: RightEye) = (x: 101.3374786376953, y: 192.5484619140625, z: -220.3924865722656)
 MLKPoseLandmarkType(_rawValue: MouthRight) = (x: 115.9384231567383, y: 190.4478302001953, z: -204.7795104980469)
 MLKPoseLandmarkType(_rawValue: LeftWrist) = (x: 241.2456665039062, y: 129.8231201171875, z: -122.1505432128906)
 MLKPoseLandmarkType(_rawValue: LeftShoulder) = (x: 152.4018249511719, y: 150.8176574707031, z: -112.9083938598633)
 MLKPoseLandmarkType(_rawValue: RightHeel) = (x: 355.2445373535156, y: 209.9060211181641, z: 215.0716857910156)
 MLKPoseLandmarkType(_rawValue: RightShoulder) = (x: 155.4138488769531, y: 215.0807342529297, z: -101.4621200561523)

 */

// 서있는 자세인데... ios는 x, y 좌표가 바뀌어서 넘겨 받습니다.

enum DebugLandMarkType: Int {
    case nose = 0
    case leftEyeInner
    case leftEye
    case leftEyeOuter
    case rightEyeInner
    case rightEye
    case rightEyeOuter
    case leftEar
    case rightEar
    case mouthLeft
    case mouthRight
    case leftShoulder
    case rightShoulder
    case leftElbow
    case rightElbow
    case leftWrist
    case rightWrist
    case leftPinkyFinger
    case rightPinkyFinger
    case leftIndexFinger
    case rightIndexFinger
    case leftThumb
    case rightThumb
    case leftHip
    case rightHip
    case leftKnee
    case rightKnee
    case leftAnkle
    case rightAnkle
    case leftHeel
    case rightHeel
    case leftToe
    case rightToe
}


class DebugSkeletonView: UIView {
    //var landmarks:[DebugLandMark]?
    let landmarks:[DebugLandMark] = [
        DebugLandMark(type: .nose, x: 106.3996734619141, y: 185.3828887939453, z: -229.8639984130859),
        DebugLandMark(type: .leftEyeInner, x: 100.5455627441406, y: 181.18115234375, z: -221.0390472412109),
        DebugLandMark(type: .leftEye, x: 100.686279296875, y: 178.0907135009766, z: -221.0595703125),
        DebugLandMark(type: .leftEyeOuter, x: 100.9446716308594, y: 175.0484466552734, z: -221.0108642578125),
        DebugLandMark(type: .rightEyeInner, x: 100.9715347290039, y: 189.8721008300781, z: -220.3697509765625),
        DebugLandMark(type: .rightEye, x: 101.3374786376953, y: 192.5484619140625, z: -220.3924865722656),
        DebugLandMark(type: .rightEyeOuter, x: 101.8272705078125, y: 195.16357421875, z: -220.2978515625),
        DebugLandMark(type: .leftEar, x: 105.3301467895508, y: 171.0313415527344, z: -158.8118133544922),
        DebugLandMark(type: .rightEar, x: 107.5618896484375, y: 198.3082427978516, z: -155.3134307861328),
        DebugLandMark(type: .mouthLeft, x: 115.4542922973633, y: 178.8846588134766, z: -205.7475128173828),
        DebugLandMark(type: .mouthRight, x: 115.9384231567383, y: 190.4478302001953, z: -204.7795104980469),
        DebugLandMark(type: .leftShoulder, x: 152.4018249511719, y: 150.8176574707031, z: -112.9083938598633),
        DebugLandMark(type: .rightShoulder, x: 155.4138488769531, y: 215.0807342529297, z: -101.4621200561523),
        DebugLandMark(type: .leftElbow, x: 200.4297637939453, y: 139.8223724365234, z: -85.78042602539062),
        DebugLandMark(type: .rightElbow, x: 201.9906005859375, y: 227.9167633056641, z: -73.82023620605469),
        DebugLandMark(type: .leftWrist, x: 241.2456665039062, y: 129.8231201171875, z: -122.1505432128906),
        DebugLandMark(type: .rightWrist, x: 240.494384765625, y: 236.8681640625, z: -111.2972030639648),
        DebugLandMark(type: .leftPinkyFinger, x: 254.5600280761719, y: 125.0765075683594, z: -136.8862457275391),
        DebugLandMark(type: .rightPinkyFinger, x: 253.9851379394531, y: 241.6973266601562, z: -126.6543045043945),
        DebugLandMark(type: .leftIndexFinger, x: 255.6887359619141, y: 127.5059967041016, z: -155.846923828125),
        DebugLandMark(type: .rightIndexFinger, x: 255.4426879882812, y: 238.9244689941406, z: -145.9014587402344),
        DebugLandMark(type: .leftThumb, x: 252.0674743652344, y: 130.4942474365234, z: -130.8651123046875),
        DebugLandMark(type: .rightThumb, x: 250.1357421875, y: 235.5218963623047, z: -120.390510559082),
        DebugLandMark(type: .leftHip, x: 236.3049163818359, y: 166.5417022705078, z: -2.930918455123901),
        DebugLandMark(type: .rightHip, x: 236.4964294433594, y: 202.3072814941406, z: 2.907700061798096),
        DebugLandMark(type: .leftKnee, x: 298.0560607910156, y: 163.3784637451172, z: 21.2111644744873),
        DebugLandMark(type: .rightKnee, x: 299.7515869140625, y: 208.8989105224609, z: 46.43084716796875),
        DebugLandMark(type: .leftAnkle, x: 350.1436157226562, y: 160.1427307128906, z: 172.5124359130859),
        DebugLandMark(type: .rightAnkle, x: 348.7644653320312, y: 213.649169921875, z: 202.7643737792969),
        DebugLandMark(type: .leftHeel, x: 355.5595703125, y: 163.6918029785156, z: 183.832275390625),
        DebugLandMark(type: .rightHeel, x: 355.2445373535156, y: 209.9060211181641, z: 215.0716857910156),
        DebugLandMark(type: .leftToe, x: 375.07080078125, y: 153.5996551513672, z: 117.4762496948242),
        DebugLandMark(type: .rightToe, x: 372.5689086914062, y: 224.1183929443359, z: 150.9266204833984),
    ]
    
    var connections: [DebugLandMarkType: [DebugLandMarkType]] = [
        .leftEar: [.leftEyeOuter],
        .leftEyeOuter: [.leftEye],
        .leftEye: [.leftEyeInner],
        .leftEyeInner: [.nose],
        .nose: [.rightEyeInner],
        .rightEyeInner: [.rightEye],
        .rightEye: [.rightEyeOuter],
        .rightEyeOuter: [.rightEar],
        .mouthLeft: [.mouthRight],
        .leftShoulder: [
            .rightShoulder,
            .leftHip,
        ],
        .rightShoulder: [
            .rightHip,
            .rightElbow,
        ],
        .rightWrist: [
            .rightElbow,
            .rightThumb,
            .rightIndexFinger,
            .rightPinkyFinger,
        ],
        .leftHip: [.rightHip, .leftKnee],
        .rightHip: [.rightKnee],
        .rightKnee: [.rightAnkle],
        .leftKnee: [.leftAnkle],
        .leftElbow: [.leftShoulder],
        .leftWrist: [
            .leftElbow, .leftThumb,
            .leftIndexFinger,
            .leftPinkyFinger,
        ],
        .leftAnkle: [.leftHeel, .leftToe],
        .rightAnkle: [.rightHeel, .rightToe],
        .rightHeel: [.rightToe],
        .leftHeel: [.leftToe],
        .rightIndexFinger: [.rightPinkyFinger],
        .leftIndexFinger: [.leftPinkyFinger],
    ]

    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    let circleSize:Double = 5
    override func draw(_ rect: CGRect) {
        let context = UIGraphicsGetCurrentContext()
        
        let color = UIColor.blue.cgColor
        context?.setFillColor(color)
        context?.setStrokeColor(color)
        
        for (startLandmarkType, endLandmarkTypes) in connections {
            let startLandmark = landmarks[startLandmarkType.rawValue]
            for endLandmarkType in endLandmarkTypes {
                let endLandmark = landmarks[endLandmarkType.rawValue]
                context?.setLineWidth(3.0)
                context?.setLineCap(.round)
                context?.move(to: CGPoint(x: startLandmark.x, y: startLandmark.y))
                context?.addLine(to: CGPoint(x: endLandmark.x, y: endLandmark.y))
                context?.strokePath()
            }
            let rectangle = CGRect(x: startLandmark.x - circleSize/2 , y: startLandmark.y - circleSize/2, width: circleSize, height: circleSize)
            context?.addEllipse(in: rectangle)
            context?.fillPath()
        }
    }
}
