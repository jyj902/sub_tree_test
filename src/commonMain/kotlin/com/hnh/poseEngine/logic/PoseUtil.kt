package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL
import kotlin.math.*

/**
 * Created by hyerim on 2021/09/24...
 */

class PoseUtil(val osal:OSAL, val exerciseGlobal: ExerciseGlobal, pose: HHPose) {
    private var nose = pose.getPoseLandmark(0)
    private var leftEyeInner = pose.getPoseLandmark(1)
    private var leftEye = pose.getPoseLandmark(2)
    private var leftEyeOuter = pose.getPoseLandmark(3)
    private var rightEyeInner = pose.getPoseLandmark(4)
    private var rightEye = pose.getPoseLandmark(5)
    private var rightEyeOuter = pose.getPoseLandmark(6)
    private var leftEar = pose.getPoseLandmark(7)
    private var rightEar = pose.getPoseLandmark(8)
    private var leftMouth = pose.getPoseLandmark(9)
    private var rightMouth = pose.getPoseLandmark(10)
    private var leftShoulder = pose.getPoseLandmark(11)
    private var rightShoulder = pose.getPoseLandmark(12)
    private var leftElbow = pose.getPoseLandmark(13)
    private var rightElbow = pose.getPoseLandmark(14)
    private var leftWrist = pose.getPoseLandmark(15)
    private var rightWrist = pose.getPoseLandmark(16)
    private var leftPinky = pose.getPoseLandmark(17)
    private var rightPinky = pose.getPoseLandmark(18)
    private var leftIndex = pose.getPoseLandmark(19)
    private var rightIndex = pose.getPoseLandmark(20)
    private var leftThumb = pose.getPoseLandmark(21)
    private var rightThumb = pose.getPoseLandmark(22)
    private var leftHip = pose.getPoseLandmark(23)
    private var rightHip = pose.getPoseLandmark(24)
    private var leftKnee = pose.getPoseLandmark(25)
    private var rightKnee = pose.getPoseLandmark(26)
    private var leftAnkle = pose.getPoseLandmark(27)
    private var rightAnkle = pose.getPoseLandmark(28)
    private var leftHeel = pose.getPoseLandmark(29)
    private var rightHeel = pose.getPoseLandmark(30)
    private var leftFootIndex = pose.getPoseLandmark(31)
    private var rightFootIndex = pose.getPoseLandmark(32)
    private fun getResize3D(
        a: PoseLandmarkPoseEngine,
        b: PoseLandmarkPoseEngine,
    ): Double{
        var aZ: Double
        val aX = (max(a.position3D.x, b.position3D.x) - min(a.position3D.x, b.position3D.x)).toDouble()
        val aY = (max(a.position3D.y, b.position3D.y) - min(a.position3D.y, b.position3D.y)).toDouble()
        if ((a.position3D.z<0)&& (b.position3D.z<0)){
            aZ = (min(b.position3D.z, a.position3D.z) - max(b.position3D.z, a.position3D.z)).toDouble()
        }else{
            aZ = (max(b.position3D.z, a.position3D.z) - min(b.position3D.z, a.position3D.z)).toDouble()
        }
        aZ *= 0.2
        return sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
    }
    private fun getResizeVertical(
        a: PoseLandmarkPoseEngine,
        b: PoseLandmarkPoseEngine,
    ): Double{
        var aZ: Double
        val aX = (max(a.position3D.x, b.position3D.x) - min(a.position3D.x, b.position3D.x)).toDouble()
        val aY = (max(a.position3D.y, b.position3D.y) - min(a.position3D.y, b.position3D.y)).toDouble()
        if ((a.position3D.z<0)&& (b.position3D.z+500<0)){
            aZ = (min(b.position3D.z+500, a.position3D.z) - max(b.position3D.z+500, a.position3D.z)).toDouble()
        }else{
            aZ = (max(b.position3D.z+500, a.position3D.z) - min(b.position3D.z+500, a.position3D.z)).toDouble()
        }
        aZ *= 0.2
        return sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
    }
    private fun getResizeSideX3D(
        a: PoseLandmarkPoseEngine,
        b: PoseLandmarkPoseEngine,
    ): Double{
        var aZ: Double
        val aX = (max(a.position3D.x, (b.position3D.x + b.position3D.x*0.5f))
                - min(a.position3D.x, (b.position3D.x + b.position3D.x*0.5f)) ).toDouble()
        val aY = (max(a.position3D.y, b.position3D.y) - min(a.position3D.y, b.position3D.y)).toDouble()
        if ((a.position3D.z<0)&& (b.position3D.z<0)){
            aZ = (min(b.position3D.z, a.position3D.z) - max(b.position3D.z, a.position3D.z)).toDouble()
        }else{
            aZ = (max(b.position3D.z, a.position3D.z) - min(b.position3D.z, a.position3D.z)).toDouble()
        }
        aZ *= 0.2
        return sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
    }
    private fun getAngle3D(
        targetPoint: PoseLandmarkPoseEngine,
        insidePoint: PoseLandmarkPoseEngine,
        lastPoint: PoseLandmarkPoseEngine
    ): Double {
        val a = getResize3D(insidePoint, lastPoint)
        val b = getResize3D(targetPoint, insidePoint)
        val c = getResize3D(targetPoint, lastPoint)
        val degree = acos((b.pow(2.0) + c.pow(2.0) - a.pow(2.0)) / (2 * b * c))
        return Math.toDegrees(degree)
    }
    private fun getAngleVertical(
        targetPoint: PoseLandmarkPoseEngine,
        subPoint: PoseLandmarkPoseEngine): Double{
        val copyTarget = targetPoint
        val a = getResizeVertical(subPoint, copyTarget)
        val b = getResize3D(targetPoint, subPoint)
        val c = getResizeVertical(targetPoint, copyTarget)
        val degree = acos((b.pow(2.0) + c.pow(2.0) - a.pow(2.0)) / (2 * b * c))
        return Math.toDegrees(degree)
    }
    private fun getAngleVerticalFront(
        targetPoint: PoseLandmarkPoseEngine,
        subPoint: PoseLandmarkPoseEngine): Double{
        val b = getResize3D(targetPoint, subPoint)
        val c = b
        var aZ: Double
        val aX = (max(subPoint.position3D.x, targetPoint.position3D.x) - min(subPoint.position3D.x, targetPoint.position3D.x)).toDouble()
        val aY = (max(subPoint.position3D.y, targetPoint.position3D.y) - min(subPoint.position3D.y, targetPoint.position3D.y)).toDouble()
        if ((subPoint.position3D.z<0)&& (targetPoint.position3D.z-c<0)){
            aZ = (min(targetPoint.position3D.z-c.toFloat(), subPoint.position3D.z) -
                    max(targetPoint.position3D.z-c.toFloat(), subPoint.position3D.z)).toDouble()
        }else{
            aZ = (max(targetPoint.position3D.z-c.toFloat(), subPoint.position3D.z) -
                    min(targetPoint.position3D.z-c.toFloat(), subPoint.position3D.z)).toDouble()
        }
        aZ *= 0.2
        val a = sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
        val degree = acos((b.pow(2.0) + c.pow(2.0) - a.pow(2.0)) / (2 * b * c))
        return Math.toDegrees(degree)
    }
    private fun getAngleVerticalBack(
        targetPoint: PoseLandmarkPoseEngine,
        subPoint: PoseLandmarkPoseEngine): Double{
        val b = getResize3D(targetPoint, subPoint)
        val c = b
        var aZ: Double
        val aX = (max(subPoint.position3D.x, targetPoint.position3D.x) - min(subPoint.position3D.x, targetPoint.position3D.x)).toDouble()
        val aY = (max(subPoint.position3D.y, targetPoint.position3D.y) - min(subPoint.position3D.y, targetPoint.position3D.y)).toDouble()
        if ((subPoint.position3D.z<0)&& (targetPoint.position3D.z<0)){
            aZ = (min(targetPoint.position3D.z+c.toFloat(), subPoint.position3D.z) -
                    max(targetPoint.position3D.z+c.toFloat(), subPoint.position3D.z)).toDouble()
        }else{
            aZ = (max(targetPoint.position3D.z+c.toFloat(), subPoint.position3D.z) -
                    min(targetPoint.position3D.z+c.toFloat(), subPoint.position3D.z)).toDouble()
        }
        aZ *= 0.2
        val a = sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
        val degree = acos((b.pow(2.0) + c.pow(2.0) - a.pow(2.0)) / (2 * b * c))
        return Math.toDegrees(degree)
    }
    private fun getAngleSideX(
        targetPoint: PoseLandmarkPoseEngine,
        subPoint: PoseLandmarkPoseEngine): Double{
        val b = getResize3D(targetPoint, subPoint)
        val c = b
        var aZ: Double
        val aX = (max(subPoint.position3D.x, (targetPoint.position3D.x + targetPoint.position3D.x*0.5f))
                - min(subPoint.position3D.x, (targetPoint.position3D.x + targetPoint.position3D.x*0.5f)) ).toDouble()
        val aY = (max(subPoint.position3D.y, targetPoint.position3D.y)
                - min(subPoint.position3D.y, targetPoint.position3D.y)).toDouble()
        if ((subPoint.position3D.z<0)&& (targetPoint.position3D.z<0)){
            aZ = (min(targetPoint.position3D.z, subPoint.position3D.z)
                    - max(targetPoint.position3D.z, subPoint.position3D.z)).toDouble()
        }else{
            aZ = (max(targetPoint.position3D.z, subPoint.position3D.z)
                    - min(targetPoint.position3D.z, subPoint.position3D.z)).toDouble()
        }
        aZ *= 0.2
        val a = sqrt(aX.pow(2.0) + aY.pow(2.0) + aZ.pow(2.0)).roundToInt().toDouble()
        val degree = acos((b.pow(2.0) + c.pow(2.0) - a.pow(2.0)) / (2 * b * c))
        return Math.toDegrees(degree)
    }
    private fun getAngle(
        firstPoint: PoseLandmarkPoseEngine,
        midPoint: PoseLandmarkPoseEngine,
        lastPoint: PoseLandmarkPoseEngine
    ): Double {
        var result = Math.toDegrees(
            (atan2(
                lastPoint.position.y - midPoint.position.y,
                lastPoint.position.x - midPoint.position.x
            )
                    - atan2(
                firstPoint.position.y - midPoint.position.y,
                firstPoint.position.x - midPoint.position.x
            )).toDouble()
        )
        result = Math.abs(result) // Angle should never be negative
        if (result > 180) {
            result = 360.0 - result // Always get the acute representation of the angle
        }
        return result
    }

    //3D 좌표계 각도 계산

    fun getHipAngle3D(): Double {
        return average(
            getAngle3D(leftHip, leftKnee, leftShoulder),
            getAngle3D(rightHip, rightKnee, rightShoulder)
        )
    }
    fun getHipAngleFront3D(): Double {
        return if(leftHip.position3D.z < rightHip.position3D.z)
            getAngle3D(leftHip, leftKnee, leftShoulder)
        else
            getAngle3D(rightHip, rightKnee, rightShoulder)
    }
    fun getKneeAngleLeft3D(): Double {
        return getAngle3D(leftKnee, leftHip, leftAnkle)
    }
    fun getKneeAngleRight3D(): Double {
        return getAngle3D(rightKnee, rightHip, rightAnkle)
    }
    fun getKneeAngleFront3D(): Double{
        return if(leftKnee.position3D.z < rightKnee.position3D.z) getKneeAngleLeft3D()
        else getKneeAngleRight3D()
    }
    fun getKneeAngle3D(): Double {
        return average(
            getAngle3D(leftKnee, leftHip, leftAnkle),
            getAngle3D(rightKnee, rightHip, rightAnkle)
        )
    }
    fun getShoulderAngle3D(): Double {
        return average(
            getAngle3D(leftShoulder, leftElbow, leftHip),
            getAngle3D(rightShoulder, rightElbow, rightHip)
        )
    }

    fun getShoulderToWristAngle3D(): Double {
        return average(
            getAngle3D(leftShoulder, leftWrist, leftHip),
            getAngle3D(rightShoulder, rightWrist, rightHip)
        )
    }

    fun getShoulderAngleFront3D(): Double {
        return if(leftShoulder.position3D.z < rightShoulder.position3D.z)
            getAngle3D(leftShoulder, leftElbow, leftHip)
        else getAngle3D(rightShoulder, rightElbow, rightHip)
    }
    //Cross Shoulder
    fun getShoulderToWristToShoulderAngle3D(): Double {
        return average(
            getAngle3D(leftShoulder, leftWrist, rightShoulder),
            getAngle3D(rightShoulder, rightWrist, leftShoulder)
        )
    }
    fun getShoulderToElbowToShoulderAngle3D(): Double {
        return average(
            getAngle3D(leftShoulder, leftElbow, rightShoulder),
            getAngle3D(rightShoulder, rightElbow, leftShoulder)
        )
    }
    fun getRightShoulderToElbowToShoulderAngle3D(): Double {
        return getAngle3D(rightShoulder, rightElbow, leftShoulder)
    }
    fun getLeftShoulderToElbowToShoulderAngle3D(): Double {
        return getAngle3D(leftShoulder, leftElbow, rightShoulder)
    }
    fun getShoulderToElbowToElbowAngle3D(): Double {
        return average(
            getAngle3D(leftShoulder, leftElbow, rightElbow),
            getAngle3D(rightShoulder, rightElbow, leftElbow)
        )
    }
    fun getFootToKnee3D(): Double {
        return average(
            getAngle3D(leftKnee, leftFootIndex, leftAnkle),
            getAngle3D(rightKnee, rightFootIndex, rightAnkle)
        )
    }
    fun getFootToKneeFront3D(): Double{
        return if(leftKnee.position3D.z < rightKnee.position3D.z){
            getAngle3D(leftKnee, leftFootIndex, leftAnkle)
        } else getAngle3D(rightKnee, rightFootIndex, rightAnkle)
    }

    fun getFrontKneeCheck(): String{
        return  if(leftKnee.position3D.z < rightKnee.position3D.z) "LeftKnee"
        else "RightKNee"
    }
    fun getFrontShoulderCheck(): String{
        return  if(leftShoulder.position3D.z < rightShoulder.position3D.z) "LeftShoulder"
        else "RightShoulder"
    }
    fun getHipKnee3D(): Double {
        return average(
            getAngle3D(leftHip,  leftKnee, rightHip),
            getAngle3D(rightHip,  rightKnee, leftHip)
        )
    }
    fun getFootAngle3D(): Double {
        return average(
            getAngle3D(leftAnkle,  leftFootIndex, rightAnkle),
            getAngle3D(rightAnkle,  rightFootIndex, leftAnkle)
        )
    }
    fun getKneeToAnkle3D(): Double{
        val hipknee = getHipKnee3D()
        val anklefoot = getFootAngle3D()
        return (max(hipknee, anklefoot) - min(hipknee, anklefoot))
    }
    fun getWrist3D(): Double {
        return getAngle3D(leftPinky, leftIndex, leftThumb)
    }
    fun getFoottoAnkleAngle3D():Double {
        return average(
            getAngle3D(leftAnkle, leftFootIndex, leftKnee),
            getAngle3D(rightAnkle, rightFootIndex, rightKnee)
        )
    }
    fun getElbowAngle3D(): Double {
        return average(
            getAngle3D(leftElbow, leftWrist, leftShoulder),
            getAngle3D(rightElbow, rightWrist, rightShoulder)
        )
    }
    fun getElbowAngleFront3D(): Double {
        return if(leftElbow.position3D.z < rightElbow.position3D.z) {
            getAngle3D(leftElbow, leftWrist, leftShoulder)
        }
        else getAngle3D(rightElbow, rightWrist, rightShoulder)
    }
    fun getPelvisFrontAngle3D(): Double {
        return if(leftKnee.position3D.z < rightKnee.position3D.z) {
            getAngle3D(leftHip, leftKnee, rightHip)
        }
        else getAngle3D(rightHip, rightKnee, leftHip)
    }

    //Vertical
    fun getElbowVertical(): Double{
        return average(getAngleVertical(leftElbow, leftWrist), getAngleVertical(rightElbow, rightWrist))
    }
    fun getShoulderVertical(): Double{
        return average(getAngleVertical(leftShoulder, leftElbow), getAngleVertical(rightShoulder, rightElbow))
    }
    fun getFootToKneeFrontVertical3D(): Double{
        return if(leftKnee.position3D.z < rightKnee.position3D.z){
            getAngleVerticalFront(leftKnee, leftAnkle)
        } else getAngleVerticalFront(rightKnee, rightAnkle)
    }
    fun getFootToKneeFrontAverageVertical3D(): Double{
        return average(getAngleVerticalFront(leftKnee, leftAnkle),
            getAngleVerticalFront(rightKnee, rightAnkle))
    }
    fun getElbowToWristBackVertical3D(): Double{
        return average(getAngleVerticalBack(leftElbow, leftWrist),
            getAngleVerticalBack(rightElbow, rightWrist))
    }

    fun getShoulderToHipFrontVertical3D(): Double{
        return average(getAngleVerticalFront(leftShoulder, leftHip),
            getAngleVerticalFront(rightShoulder, rightHip))
    }
    fun getShoulderToElbowFrontVertical3D(): Double{
        return average(getAngleVerticalFront(leftShoulder, leftElbow),
            getAngleVerticalFront(rightShoulder, rightElbow))
    }
    //SideX
    fun getAnkleSideXFront(): Double{
        return if(leftKnee.position3D.z < rightKnee.position3D.z) {
            getAngleSideX(leftAnkle, leftFootIndex)
        }else getAngleSideX(rightAnkle, rightFootIndex)
    }
    fun getKneeSideXFront(): Double{
        return if(leftKnee.position3D.z < rightKnee.position3D.z) {
            getAngleSideX(leftKnee, leftHip)
        }else getAngleSideX(rightKnee, rightHip)
    }

    //3D 거리 계산
    fun getShoulderDistance3D(): Double{
        return getResize3D(leftShoulder, rightShoulder)
    }
    fun getWristDistance(): Double{
        return getResize3D(leftWrist, rightWrist)
    }
    //2D 각도 계산

    fun getHipAngle(): Double {
        return average(
            getAngle(this.leftKnee, this.leftHip, this.leftShoulder),
            getAngle(this.rightKnee, this.rightHip, this.rightShoulder)
        )
    }

    fun getCenterAngle(): Double {
        var hipSum = max(this.rightHip.position.x, this.leftHip.position.x) -
                min(this.rightHip.position.x, this.leftHip.position.x)
        val absHipSumZ = abs(this.rightHip.position3D.z) + abs(this.leftHip.position3D.z)
        return if (this.leftHip.position3D.z > this.rightHip.position3D.z) {
            if (this.leftHip.position.x > this.rightHip.position.x) {
                Math.toDegrees(atan2(hipSum.toDouble(), absHipSumZ * 0.5))
            } else {
                hipSum = - hipSum
                Math.toDegrees(atan2(hipSum.toDouble(), absHipSumZ * 0.5))+360.0
            }
        } else {
            if (this.leftHip.position.x > this.rightHip.position.x) {
                hipSum = -hipSum
                Math.toDegrees(atan2(hipSum.toDouble(), absHipSumZ * 0.5)) +180.0
            } else {
                Math.toDegrees(atan2(hipSum.toDouble(), absHipSumZ * 0.5))+180.0
            }
        }
    }
    fun getShoulderAngle(): Double {
        return average(
            getAngle(this.leftElbow, this.leftShoulder, this.leftHip),
            getAngle(this.rightElbow, this.rightShoulder, this.rightHip)
        )
    }
    fun getElbowAngle(): Double {
        return average(
            getAngle(this.leftWrist, this.leftElbow, this.leftShoulder),
            getAngle(this.rightWrist, this.rightElbow, this.rightShoulder)
        )
    }
    fun getElbowAngleFront(): Double {
        return if(leftElbow.position3D.z < rightElbow.position3D.z)
            getAngle(this.leftWrist, this.leftElbow, this.leftShoulder)
        else getAngle(this.rightWrist, this.rightElbow, this.rightShoulder)
    }
    fun getKneetoWristDistance():Double{
        val kneeSum = average(this.leftKnee.position3D.z, this.rightKnee.position3D.z)
        val wristSum = average(this.leftWrist.position3D.z, this.rightWrist.position3D.z)
        return (max(kneeSum, wristSum) - min(kneeSum, wristSum)).toDouble()
    }

    //2D 거리
    fun getFootToShoulderDistance(): Double{
        val footSum = max(this.leftAnkle.position.x,this.rightAnkle.position.x) -
                min(this.leftAnkle.position.x, this.rightAnkle.position.x)
        val soulderSum = max(this.leftShoulder.position.x,this.rightShoulder.position.x) -
                min(this.leftShoulder.position.x, this.rightShoulder.position.x)

        return (footSum - soulderSum*0.7)
    }
    fun getShoulderDistance(): Double{
        return (max(this.leftShoulder.position.x,this.rightShoulder.position.x) -
                min(this.leftShoulder.position.x, this.rightShoulder.position.x)).toDouble()
    }
    fun getFootDistance(): Double{
        return (max(this.leftAnkle.position.x,this.rightAnkle.position.x) -
                min(this.leftAnkle.position.x, this.rightAnkle.position.x)).toDouble()
    }
    fun getKneeDistance(): Double {
        return (max(this.leftKnee.position.x, this.rightKnee.position.x) -
                min(this.leftKnee.position.x, this.rightKnee.position.x)).toDouble()
    }
    fun getPelvisDistance(): Double {
        return (max(this.leftHip.position.x, this.rightHip.position.x) -
                min(this.leftHip.position.x, this.rightHip.position.x)).toDouble()
    }

    //포인트 체크
    fun getWristToEar():Double{
        val earSum = average(this.leftEar.position3D.y, this.rightEar.position3D.y)
        val wristSum = average(this.leftWrist.position3D.y, this.rightWrist.position3D.y)
        return (earSum*1.05- wristSum).toDouble()
    }
    fun getWristToShoulder():Double{
        val shoulderSum = average(this.leftShoulder.position.y, this.rightShoulder.position.y)
        val wristSum = average(this.leftWrist.position.y, this.rightWrist.position.y)
        return (wristSum - shoulderSum).toDouble()
    }
    fun getElbowToShoulder():Double{
        val shoulderSum = average(this.leftShoulder.position3D.y, this.rightShoulder.position3D.y)
        val elbowSum = average(this.leftElbow.position3D.y, this.rightElbow.position3D.y)
        return (shoulderSum*1.1- elbowSum).toDouble()
    }
    fun getKneetoAnklePoint():Double {
        val ankleSum = average(this.leftAnkle.position3D.z, this.rightAnkle.position3D.z)
        val kneeSum = average(this.leftKnee.position3D.z, this.rightKnee.position3D.z)
        return if (kneeSum < ankleSum) (kneeSum / ankleSum).toDouble()
        else (ankleSum / kneeSum).toDouble()
    }
    fun testRightWristZ():Double{
        return this.rightWrist.position3D.z.toDouble()
    }
    fun testLeftWristZ():Double{
        return this.leftWrist.position3D.z.toDouble()
    }
    fun getKneetoWristPointY():Double{
        val wristSum = average(this.leftWrist.position.y, this.rightWrist.position.y)
        val kneeSum = average(this.leftKnee.position.y, this.rightKnee.position.y)
        return (kneeSum*0.9 - wristSum)
    }
    fun getKneetoWristPointZ():Double{
        val wristSum = average(this.leftWrist.position3D.z, this.rightWrist.position3D.z)
        val kneeSum = average(this.leftKnee.position3D.z, this.rightKnee.position3D.z)
        return if(wristSum<0 && kneeSum<0) abs( 1 / kneeSum - wristSum).toDouble()
        else if( wristSum>0 && kneeSum<0) abs( 1 / (wristSum - kneeSum)).toDouble()
        else if( wristSum<0 && kneeSum>0) abs(1 / (-wristSum + kneeSum)).toDouble()
        else abs(1 / (-wristSum + kneeSum)).toDouble()
    }
    fun getWristtoShoulderPointZ():Double{
        val wristSum = average(this.leftWrist.position3D.z, this.rightWrist.position3D.z)
        val shoulderSum = average(this.leftShoulder.position3D.z, this.rightShoulder.position3D.z)
        return if(wristSum<0 && shoulderSum<0) abs(wristSum - shoulderSum).toDouble()
        else if( wristSum>0 && shoulderSum<0) abs(wristSum - shoulderSum).toDouble()
        else if( wristSum<0 && shoulderSum>0) abs(shoulderSum - wristSum).toDouble()
        else abs(wristSum + shoulderSum).toDouble()
    }
    fun getKneePointZ():Double{
        val kneeSum = average(this.leftKnee.position3D.z, this.rightKnee.position3D.z)
        return kneeSum.toDouble()
    }
    fun getWristPointZ():Double{
        val wristSum = average(this.leftWrist.position3D.z, this.rightWrist.position3D.z)
        return wristSum.toDouble()
    }
    fun getShoulderPointZ():Double{
        val shoulderSum = average(this.leftShoulder.position3D.z, this.rightShoulder.position3D.z)
        return shoulderSum.toDouble()
    }
    fun getWristToShuolderPointY():Double{
        val wristSum = average(this.leftWrist.position.y, this.rightWrist.position.y)
        val shoulderSum = average(this.leftShoulder.position.y, this.rightShoulder.position.y)
        return (wristSum*1.15 - shoulderSum)
    }
    fun getWrisTtoElbowPointY():Double{
        val wristSum = average(this.leftWrist.position.y, this.rightWrist.position.y)
        val elbowSum = average(this.leftElbow.position.y, this.rightElbow.position.y)
        return (wristSum - elbowSum* 0.9)
    }
    fun getRightWristToShuolderPointY():Double{
        val wrist = rightWrist.position.y
        val shoulder = rightShoulder.position.y
        return (wrist*1.15 - shoulder)
    }
    fun getLeftWristToShuolderPointY():Double{
        val wrist = leftWrist.position.y
        val shoulder = leftShoulder.position.y
        return (wrist*1.15 - shoulder)
    }
    //운동 시작 종료 시그널

    fun startHand():Boolean{
        return try {
            val leftHandAngle = getAngle3D(leftElbow, leftShoulder, leftWrist)
            val rightHandAngle = getAngle3D(rightElbow, rightShoulder, rightWrist)
            if ((rightHandAngle >100) && (rightHandAngle <130) &&
                (leftHandAngle >90) && (leftHandAngle <145)) {
                (leftWrist.position3D.y < (leftShoulder.position3D.y)*0.97) &&
                        (rightWrist.position3D.y*0.97 > (rightShoulder.position3D.y))
            } else false
        } catch(e:Exception){
            osal.logE("error", e.toString())
            false
        }
    }
    fun stopHand():Boolean{
        return try {
            val leftHandAngle = getAngle3D(leftElbow, leftShoulder, leftWrist)
            val rightHandAngle = getAngle3D(rightElbow, rightShoulder, rightWrist)
            if ((rightHandAngle >90) && (rightHandAngle <145) &&
                (leftHandAngle >100) && (leftHandAngle <130)) {
                (leftWrist.position3D.y*0.97 > (leftShoulder.position3D.y) ) &&
                        (rightWrist.position3D.y < (rightShoulder.position3D.y) * 0.97)
            }else false
        } catch(e:Exception){
            osal.logD("error", e.toString())
            false
        }
    }

    //화면 전신 체크
    fun checkFrameFull():Boolean{
        return try{
            ((this.leftWrist.inFrameLikelihood >0.97)&&
                    (this.rightWrist.inFrameLikelihood >0.97)&&
                    (this.leftAnkle.inFrameLikelihood >0.97)&&
                    (this.rightAnkle.inFrameLikelihood >0.97))
        } catch (e : Exception){
            false
        }
    }
    fun checkFrameUpper():Boolean{
        return try{
            ((this.leftWrist.inFrameLikelihood >0.97)&&
                    (this.rightWrist.inFrameLikelihood >0.97)&&
                    (this.leftShoulder.inFrameLikelihood >0.97)&&
                    (this.rightShoulder.inFrameLikelihood >0.97))
        } catch (e : Exception){
            false
        }
    }
    fun checkFrameLower():Boolean{
        return try{
            ((this.leftHip.inFrameLikelihood >0.97)&&
                    (this.rightHip.inFrameLikelihood >0.97)&&
                    (this.rightKnee.inFrameLikelihood >0.97)&&
                    (this.leftKnee.inFrameLikelihood >0.97)&&
                    (this.leftAnkle.inFrameLikelihood >0.97)&&
                    (this.rightAnkle.inFrameLikelihood >0.97))
        } catch (e : Exception){
            false
        }
    }
    fun checkFrameFull(Debugger:Boolean):String{
        return try{
            val str = ""
            (str +"$this.leftWrist.inFrameLikelihood\n"+
                    "$this.rightWrist.inFrameLikelihood\n"+
                    "$this.leftAnkle.inFrameLikelihood\n"+
                    "$this.rightAnkle.inFrameLikelihood")
        } catch (e : Exception){
            "false"
        }
    }
    companion object{

    }
}

fun average(x: Float, x1: Float): Float {
    return (x + x1) / 2
}

fun average(x: Double, x1: Double): Double {
    return (x + x1) / 2
}

