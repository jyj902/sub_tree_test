package com.hnh.presentation.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class PoseGraphic internal constructor(
    overlay: GraphicOverlay,
    private val pose: Pose,
    private val isPoseGraphic : Boolean,
    private val listQueue : ArrayList<MutableMap<String, Boolean>>,
    private val exerciseType : Int,
    private val isStart : Boolean
) : GraphicOverlay.Graphic(overlay) {
    private val successPaint: Paint = Paint()
    private val errorPaint: Paint = Paint()
    private val whitePaint: Paint = Paint()
    private var bluePaint: Paint = Paint()
    private val puplePaint: Paint = Paint()
    private var checkPaint: Paint = Paint()
    private val textPaint: Paint = Paint()

    init {
        textPaint.strokeWidth = STROKE_WIDTH
        textPaint.color = Color.rgb(233,30,99)
        textPaint.textSize = 60F
        textPaint.setShadowLayer(6.0f, 0f, 0f, Color.WHITE)
        textPaint.typeface = Typeface.DEFAULT_BOLD
        whitePaint.strokeWidth = STROKE_WIDTH
        whitePaint.color = Color.WHITE
        successPaint.strokeWidth = STROKE_WIDTH
        successPaint.color = Color.GREEN
        errorPaint.strokeWidth = STROKE_WIDTH
        errorPaint.color = Color.RED
        bluePaint.strokeWidth = STROKE_WIDTH
        bluePaint.color = Color.BLUE
        checkPaint.strokeWidth = STROKE_WIDTH
        checkPaint.color = Color.BLUE
        puplePaint.strokeWidth = STROKE_WIDTH
        puplePaint.color = Color.rgb(107,63, 160)// puple
    }

    override fun draw(canvas: Canvas) {
        val landmarks = pose.allPoseLandmarks
        if (landmarks.isEmpty()) {
            return
        }
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)

        val sumLeftShoulder = stableData(
            stableLeftShoulder, leftShoulder.position3D.x,
            leftShoulder.position3D.y)
        val sumRightShoulder = stableData(
            stableRightShoulder, rightShoulder.position3D.x,
            rightShoulder.position3D.y)
        val sumLeftElbow = stableData(
            stableLeftElbow, leftElbow.position3D.x,
            leftElbow.position3D.y)
        val sumRightElbow = stableData(
            stableRightElbow, rightElbow.position3D.x,
            rightElbow.position3D.y)
        val sumLeftWrist = stableData(
            stableLeftWrist, leftWrist.position3D.x,
            leftWrist.position3D.y)
        val sumRightWrist = stableData(
            stableRightWrist, rightWrist.position3D.x,
            rightWrist.position3D.y)
        val sumLeftHip = stableData(
            stableLeftHip, leftHip.position3D.x,
            leftHip.position3D.y)
        val sumRightHip = stableData(
            stableRightHip, rightHip.position3D.x,
            rightHip.position3D.y)
        val sumLeftKnee = stableData(
            stableLeftKnee, leftKnee.position3D.x,
            leftKnee.position3D.y)
        val sumRightKnee = stableData(
            stableRightKnee, rightKnee.position3D.x,
            rightKnee.position3D.y)
        val sumLeftAnkle = stableData(
            stableLeftAnkle, leftAnkle.position3D.x,
            leftAnkle.position3D.y)
        val sumRightAnkle = stableData(
            stableRightAnkle, rightAnkle.position3D.x,
            rightAnkle.position3D.y)
        val sumRightHeel = stableData(
            stableRightHeel, rightHeel.position3D.x,
            rightHeel.position3D.y)
        val sumLeftHeel = stableData(
            stableLeftHeel, leftHeel.position3D.x,
            leftHeel.position3D.y)
        val sumRightFootIndex = stableData(
            stableRightFootIndex, rightFootIndex.position3D.x,
            rightFootIndex.position3D.y)
        val sumLeftFootIndex = stableData(
            stableLeftFootIndex, leftFootIndex.position3D.x,
            leftFootIndex.position3D.y)
        try {
            if (isPoseGraphic && isStart) {
                when (exerciseType) {
                    EXERCISE_INFORMATION.FULL -> {
                        if (checkMoving(
                                leftElbow, rightElbow, leftHip, rightHip,
                                leftWrist, rightWrist, leftKnee, rightKnee,
                                previosLeftElbowPoint, previosRightElbowPoint,
                                previosLeftHipPoint, previosRightHipPoint,
                                previosLeftWristPoint, previosRightWristPoint,
                                previosLeftShoulderPoint, previosRightShoulderPoint
                            )
                        ) {
                            //엉덩이 - 무릎 - 발목
                            drawLine(canvas,rightKnee,rightAnkle,successPaint,sumRightKnee,sumRightAnkle)
                            drawLine(canvas,leftKnee,leftAnkle,successPaint,sumLeftKnee,sumLeftAnkle)
                            drawLine(canvas,leftHip,leftKnee,successPaint,sumLeftHip,sumLeftKnee)
                            drawLine(canvas,rightHip,rightKnee,successPaint,sumRightHip,sumRightKnee)
                            //발
                            drawLine(canvas,rightAnkle,rightHeel,successPaint,sumRightAnkle,sumRightHeel)
                            drawLine(canvas, rightAnkle, rightFootIndex, successPaint, sumRightAnkle, sumRightFootIndex)
                            drawLine(canvas,rightHeel,rightFootIndex,successPaint,sumRightHeel,sumRightFootIndex)
                            drawLine(canvas,leftAnkle,leftHeel,successPaint,sumLeftAnkle,sumLeftHeel)
                            drawLine(canvas, leftAnkle, leftFootIndex, successPaint, sumLeftAnkle, sumLeftFootIndex)
                            drawLine(canvas,leftHeel,leftFootIndex,successPaint,sumLeftHeel,sumLeftFootIndex)
                            //몸통
                            drawLine( canvas, leftShoulder, leftHip, successPaint, sumLeftShoulder, sumLeftHip )
                            drawLine( canvas, leftShoulder, rightShoulder, successPaint, sumLeftShoulder, sumRightShoulder )
                            drawLine( canvas, leftHip, rightHip, successPaint, sumLeftHip, sumRightHip )
                            drawLine( canvas, rightShoulder, rightHip, successPaint, sumRightShoulder, sumRightHip )
                            //어깨 - 손목
                            drawLine( canvas, leftShoulder, leftElbow, successPaint, sumLeftShoulder, sumLeftElbow )
                            drawLine( canvas, leftElbow, leftWrist, successPaint, sumLeftElbow, sumLeftWrist )
                            drawLine( canvas, rightShoulder, rightElbow, successPaint, sumRightShoulder, sumRightElbow )
                            drawLine( canvas, rightElbow, rightWrist, successPaint, sumRightElbow, sumRightWrist )
                        } else {
                            //Line-------------------------------------------------------
                            //엉덩이 - 무릎 - 발목
                            drawLine(canvas, rightKnee, rightAnkle, successPaint)
                            drawLine(canvas, leftKnee, leftAnkle, successPaint)
                            drawLine(canvas, leftHip, leftKnee, successPaint)
                            drawLine(canvas, rightHip, rightKnee, successPaint)
                            //발
                            drawLine(canvas, rightAnkle, rightHeel, successPaint)
                            drawLine(canvas, rightAnkle, rightFootIndex, successPaint)
                            drawLine(canvas, rightHeel, rightFootIndex, successPaint)
                            drawLine(canvas, leftAnkle, leftHeel, successPaint)
                            drawLine(canvas, leftAnkle, leftFootIndex, successPaint)
                            drawLine(canvas, leftHeel, leftFootIndex, successPaint)
                            //몸통
                            drawLine(canvas, leftShoulder, leftHip, successPaint)
                            drawLine(canvas, leftShoulder, rightShoulder, successPaint)
                            drawLine(canvas, leftHip, rightHip, successPaint)
                            drawLine(canvas, rightShoulder, rightHip, successPaint)
                            //어깨 - 손목
                            drawLine(canvas, leftShoulder, leftElbow, successPaint)
                            drawLine(canvas, leftElbow, leftWrist, successPaint)
                            drawLine(canvas, rightShoulder, rightElbow, successPaint)
                            drawLine(canvas, rightElbow, rightWrist, successPaint)
                        }

                        //Point---------------------------------------------------------
                        drawPoint(canvas, leftKnee, whitePaint)
                        drawPoint(canvas, rightKnee, whitePaint)
                        drawPoint(canvas, leftAnkle, whitePaint)
                        drawPoint(canvas, rightAnkle, whitePaint)
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)
                        drawPoint(canvas, leftWrist, whitePaint)
                        drawPoint(canvas, rightWrist, whitePaint)
                        drawPoint(canvas, leftElbow, whitePaint)
                        drawPoint(canvas, rightElbow, whitePaint)
                    }
                    EXERCISE_INFORMATION.UPPER -> {
                        //Line------------------------------------------------------------
                        if (checkMoving(
                                leftElbow, rightElbow, leftWrist, rightWrist,
                                previosLeftElbowPoint, previosRightElbowPoint,
                                previosLeftWristPoint, previosRightWristPoint
                            )
                        ) {
                            //몸통
                            drawLine( canvas, leftShoulder, leftHip, successPaint, sumLeftShoulder, sumLeftHip )
                            drawLine( canvas, leftShoulder, rightShoulder, successPaint, sumLeftShoulder, sumRightShoulder )
                            drawLine( canvas, leftHip, rightHip, successPaint, sumLeftHip, sumRightHip )
                            drawLine( canvas, rightShoulder, rightHip, successPaint, sumRightShoulder, sumRightHip )
                            //어깨 - 손목
                            drawLine( canvas, leftShoulder, leftElbow, successPaint, sumLeftShoulder, sumLeftElbow )
                            drawLine( canvas, leftElbow, leftWrist, successPaint, sumLeftElbow, sumLeftWrist )
                            drawLine( canvas, rightShoulder, rightElbow, successPaint, sumRightShoulder, sumRightElbow )
                            drawLine( canvas, rightElbow, rightWrist, successPaint, sumRightElbow, sumRightWrist )
                        } else {
                            //Line-------------------------------------------------------
                            //몸통
                            drawLine(canvas, leftShoulder, leftHip, successPaint)
                            drawLine(canvas, leftShoulder, rightShoulder, successPaint)
                            drawLine(canvas, leftHip, rightHip, successPaint)
                            drawLine(canvas, rightShoulder, rightHip, successPaint)
                            //어깨 - 손목
                            drawLine(canvas, leftShoulder, leftElbow, successPaint)
                            drawLine(canvas, leftElbow, leftWrist, successPaint)
                            drawLine(canvas, rightShoulder, rightElbow, successPaint)
                            drawLine(canvas, rightElbow, rightWrist, successPaint)
                        }

                        //Point---------------------------------------------------------
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)

                        drawPoint(canvas, leftWrist, whitePaint)
                        drawPoint(canvas, rightWrist, whitePaint)
                        drawPoint(canvas, leftElbow, whitePaint)
                        drawPoint(canvas, rightElbow, whitePaint)
                    }
                    EXERCISE_INFORMATION.LOWER -> {
                        //Line------------------------------------------------------------
                        if (checkMoving(
                                leftHip, rightHip, leftKnee, rightKnee,
                                previosLeftHipPoint, previosRightHipPoint,
                                previosLeftShoulderPoint, previosRightShoulderPoint
                            )
                        ) {
                            //엉덩이 - 무릎 - 발목
                            drawLine(canvas,rightKnee,rightAnkle,successPaint,sumRightKnee,sumRightAnkle)
                            drawLine(canvas,leftKnee,leftAnkle,successPaint,sumLeftKnee,sumLeftAnkle)
                            drawLine(canvas,leftHip,leftKnee,successPaint,sumLeftHip,sumLeftKnee)
                            drawLine(canvas,rightHip,rightKnee,successPaint,sumRightHip,sumRightKnee)
                            //발
                            drawLine(canvas,rightAnkle,rightHeel,successPaint,sumRightAnkle,sumRightHeel)
                            drawLine(canvas, rightAnkle, rightFootIndex, successPaint, sumRightAnkle, sumRightFootIndex)
                            drawLine(canvas,rightHeel,rightFootIndex,successPaint,sumRightHeel,sumRightFootIndex)
                            drawLine(canvas,leftAnkle,leftHeel,successPaint,sumLeftAnkle,sumLeftHeel)
                            drawLine(canvas, leftAnkle, leftFootIndex, successPaint, sumLeftAnkle, sumLeftFootIndex)
                            drawLine(canvas,leftHeel,leftFootIndex,successPaint,sumLeftHeel,sumLeftFootIndex)
                            //몸통
                            drawLine( canvas, leftShoulder, leftHip, successPaint, sumLeftShoulder, sumLeftHip )
                            drawLine( canvas, leftShoulder, rightShoulder, successPaint, sumLeftShoulder, sumRightShoulder )
                            drawLine( canvas, leftHip, rightHip, successPaint, sumLeftHip, sumRightHip )
                            drawLine( canvas, rightShoulder, rightHip, successPaint, sumRightShoulder, sumRightHip )
                        } else {
                            //Line-------------------------------------------------------
                            //엉덩이 - 무릎 - 발목
                            drawLine(canvas, rightKnee, rightAnkle, successPaint)
                            drawLine(canvas, leftKnee, leftAnkle, successPaint)
                            drawLine(canvas, leftHip, leftKnee, successPaint)
                            drawLine(canvas, rightHip, rightKnee, successPaint)
                            //발
                            drawLine(canvas, rightAnkle, rightHeel, successPaint)
                            drawLine(canvas, rightAnkle, rightFootIndex, successPaint)
                            drawLine(canvas, rightHeel, rightFootIndex, successPaint)
                            drawLine(canvas, leftAnkle, leftHeel, successPaint)
                            drawLine(canvas, leftAnkle, leftFootIndex, successPaint)
                            drawLine(canvas, leftHeel, leftFootIndex, successPaint)
                            //몸통
                            drawLine(canvas, leftShoulder, leftHip, successPaint)
                            drawLine(canvas, leftShoulder, rightShoulder, successPaint)
                            drawLine(canvas, leftHip, rightHip, successPaint)
                            drawLine(canvas, rightShoulder, rightHip, successPaint)
                        }

                        //Point---------------------------------------------------------
                        drawPoint(canvas, leftKnee, whitePaint)
                        drawPoint(canvas, rightKnee, whitePaint)
                        drawPoint(canvas, leftAnkle, whitePaint)
                        drawPoint(canvas, rightAnkle, whitePaint)

                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)
                    }
                }
            }
            else if (!isPoseGraphic && isStart) {
                //PoseComent
                //Point---------------------------------------------------------
                if (updatePaintColor("FootDistance", listQueue)) {
                    drawPoint(canvas, leftAnkle, errorPaint)
                    drawPoint(canvas, rightAnkle, errorPaint)
                    drawText("  어깨넓이", canvas, leftAnkle, textPaint)
                }
                if (updatePaintColor("BarWrist", listQueue)) {
                    drawPoint(canvas, leftWrist, errorPaint)
                    drawPoint(canvas, rightWrist, errorPaint)
                    drawText("  손목", canvas, leftWrist, textPaint)
                }
                if (updatePaintColor("Center", listQueue)) {
                    drawPoint(canvas, leftHip, errorPaint)
                    drawPoint(canvas, rightHip, errorPaint)
                    drawText("  정면주시", canvas, leftHip, textPaint)
                }
                if (updatePaintColor("KneeDistance", listQueue)) {
                    drawPoint(canvas, leftKnee, errorPaint)
                    drawPoint(canvas, rightKnee, errorPaint)
                    drawText("  무릎", canvas, leftKnee, textPaint)
                }
                if (updatePaintColor("ShoulderToWrist", listQueue)) {
                    drawPoint(canvas, leftWrist, errorPaint)
                    drawPoint(canvas, rightWrist, errorPaint)
                    drawText("  손목 위치", canvas, leftElbow, textPaint)
                }
                if (updatePaintColor("HipAndKnee", listQueue)) {
                    drawPoint(canvas, leftShoulder, errorPaint)
                    drawPoint(canvas, rightShoulder, errorPaint)
                    drawText("  허리 무릎 동시", canvas, leftElbow, textPaint)
                }
                if (updatePaintColor("ElbowAngle", listQueue)) {
                    drawPoint(canvas, leftElbow, errorPaint)
                    drawPoint(canvas, rightElbow, errorPaint)
                    drawText("  팔꿈치 일자", canvas, rightShoulder, textPaint)
                }
                if (updatePaintColor("SPElbowAngle", listQueue)) {
                    drawPoint(canvas, leftElbow, errorPaint)
                    drawPoint(canvas, rightElbow, errorPaint)
                    drawText("  팔꿈치 떨어짐", canvas, rightShoulder, textPaint)
                }
                if (updatePaintColor("ElbowVertical", listQueue)) {
                    drawPoint(canvas, leftElbow, errorPaint)
                    drawPoint(canvas, rightElbow, errorPaint)
                    drawText("  팔꿈치 직선으로", canvas, leftElbow, textPaint)
                }

                //Line------------------------------------------------------------
                if (updatePaintColor("HipAngleUp", listQueue)) {
                    drawLine(canvas, rightKnee, rightAnkle, errorPaint)
                    drawLine(canvas, leftKnee, leftAnkle, errorPaint)
                    drawText("  허리", canvas, leftKnee, textPaint)
                }
                if (updatePaintColor("HipAngleDown", listQueue)) {
                    drawLine(canvas, leftShoulder, leftHip, errorPaint)
                    drawLine(canvas, leftHip, leftKnee, errorPaint)
                    drawLine(canvas, rightShoulder, rightHip, errorPaint)
                    drawLine(canvas, rightHip, rightKnee, errorPaint)
                    drawText("  엉덩이", canvas, rightKnee, textPaint)
                }
                if (updatePaintColor("KneePoint", listQueue)) {
                    drawLine(canvas, leftAnkle, leftKnee, errorPaint)
                    drawLine(canvas, leftHip, leftKnee, errorPaint)
                    drawLine(canvas, rightAnkle, rightKnee, errorPaint)
                    drawLine(canvas, rightHip, rightKnee, errorPaint)
                    drawText(" 무릎을 뒤로", canvas, leftHip, textPaint)
                }

                if (updatePaintColor("HipAngle", listQueue)) {
                    drawLine(canvas, leftHip, leftKnee, errorPaint)
                    drawLine(canvas, leftHip, leftShoulder, errorPaint)
                    drawLine(canvas, rightHip, rightKnee, errorPaint)
                    drawLine(canvas, rightHip, rightShoulder, errorPaint)
                    drawText("  허리", canvas, leftHip, textPaint)
                }
                if (updatePaintColor("Wrist3D", listQueue)) {
                    drawLine(canvas, leftWrist, leftElbow, errorPaint)
                    drawLine(canvas, leftElbow, leftShoulder, errorPaint)
                    drawLine(canvas, rightWrist, rightElbow, errorPaint)
                    drawLine(canvas, rightElbow, rightShoulder, errorPaint)
                    drawText("  손을옆으로", canvas, leftElbow, textPaint)
                }
                if (updatePaintColor("DownElbow", listQueue)) {
                    drawLine(canvas, leftHip, leftElbow, errorPaint)
                    drawLine(canvas, leftElbow, leftShoulder, errorPaint)
                    drawLine(canvas, rightHip, rightElbow, errorPaint)
                    drawLine(canvas, rightElbow, rightShoulder, errorPaint)
                    drawText("  팔꿈치위로", canvas, leftElbow, textPaint)
                }
                if (updatePaintColor("ShoulderPacking", listQueue)) {
                    drawLine(canvas, leftElbow, leftShoulder, errorPaint)
                    drawLine(canvas, leftShoulder, rightShoulder, errorPaint)
                    drawLine(canvas, rightShoulder, rightElbow, errorPaint)
                    drawText("  어깨 ", canvas, leftShoulder, textPaint)
                }
            }
            else if (!isStart) {
//                val utils = PoseUtil(pose)
//                val checkFrame = when(exerciseType){
//                    EXERCISE_INFORMATION.FULL -> {
//                        utils.checkFrameFull()
//                    }
//                    EXERCISE_INFORMATION.UPPER -> {
//                        utils.checkFrameUpper()
//                    }
//                    EXERCISE_INFORMATION.LOWER -> {
//                        utils.checkFrameLower()
//                    }
//                    else -> {
//                        utils.checkFrameFull()
//                    }
//                }
//                checkPaint = if (checkFrame) bluePaint
//                else puplePaint
                when(exerciseType){
                    EXERCISE_INFORMATION.FULL -> {
                        drawPoint(canvas, leftKnee, whitePaint)
                        drawPoint(canvas, rightKnee, whitePaint)
                        drawPoint(canvas, leftAnkle, whitePaint)
                        drawPoint(canvas, rightAnkle, whitePaint)
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)
                        drawPoint(canvas, leftWrist, whitePaint)
                        drawPoint(canvas, rightWrist, whitePaint)
                        drawPoint(canvas, leftElbow, whitePaint)
                        drawPoint(canvas, rightElbow, whitePaint)
                        //Line------------------------------------------------------------
                        drawLine(canvas, rightKnee, rightAnkle, checkPaint)
                        drawLine(canvas, leftKnee, leftAnkle, checkPaint)
                        drawLine(canvas, leftShoulder, leftHip, checkPaint)
                        drawLine(canvas, leftHip, leftKnee, checkPaint)
                        drawLine(canvas, rightShoulder, rightHip, checkPaint)
                        drawLine(canvas, rightHip, rightKnee, checkPaint)
                        //몸통
                        drawLine(canvas, leftShoulder, rightShoulder, checkPaint)
                        drawLine(canvas, leftHip, rightHip, checkPaint)
                        //어깨 - 손목
                        drawLine(canvas, leftShoulder, leftElbow, checkPaint)
                        drawLine(canvas, leftElbow, leftWrist, checkPaint)

                        drawLine(canvas, rightShoulder, rightElbow, checkPaint)
                        drawLine(canvas, rightElbow, rightWrist, checkPaint)

                        //발
                        drawLine(canvas, rightAnkle, rightHeel, checkPaint)
                        drawLine(canvas, rightAnkle, rightFootIndex, checkPaint)
                        drawLine(canvas, rightHeel, rightFootIndex, checkPaint)

                        drawLine(canvas, leftAnkle, leftHeel, checkPaint)
                        drawLine(canvas, leftAnkle, leftFootIndex, checkPaint)
                        drawLine(canvas, leftHeel, leftFootIndex, checkPaint)
                    }
                    EXERCISE_INFORMATION.UPPER -> {
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)
                        drawPoint(canvas, leftWrist, whitePaint)
                        drawPoint(canvas, rightWrist, whitePaint)
                        drawPoint(canvas, leftElbow, whitePaint)
                        drawPoint(canvas, rightElbow, whitePaint)
                        //Line------------------------------------------------------------
                        //몸통
                        drawLine(canvas, leftShoulder, leftHip, checkPaint)
                        drawLine(canvas, rightShoulder, rightHip, checkPaint)
                        drawLine(canvas, leftShoulder, rightShoulder, checkPaint)
                        drawLine(canvas, leftHip, rightHip, checkPaint)
                        //어깨 - 손목
                        drawLine(canvas, leftShoulder, leftElbow, checkPaint)
                        drawLine(canvas, leftElbow, leftWrist, checkPaint)

                        drawLine(canvas, rightShoulder, rightElbow, checkPaint)
                        drawLine(canvas, rightElbow, rightWrist, checkPaint)
                    }
                    EXERCISE_INFORMATION.LOWER -> {
                        drawPoint(canvas, leftKnee, whitePaint)
                        drawPoint(canvas, rightKnee, whitePaint)
                        drawPoint(canvas, leftAnkle, whitePaint)
                        drawPoint(canvas, rightAnkle, whitePaint)
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        drawPoint(canvas, leftShoulder, whitePaint)
                        drawPoint(canvas, rightShoulder, whitePaint)
                        //Line------------------------------------------------------------

                        //하체
                        drawLine(canvas, rightKnee, rightAnkle, checkPaint)
                        drawLine(canvas, leftKnee, leftAnkle, checkPaint)
                        drawLine(canvas, leftHip, leftKnee, checkPaint)
                        drawLine(canvas, rightHip, rightKnee, checkPaint)
                        //몸통
                        drawLine(canvas, leftShoulder, leftHip, checkPaint)
                        drawLine(canvas, leftShoulder, rightShoulder, checkPaint)
                        drawLine(canvas, leftHip, rightHip, checkPaint)
                        drawLine(canvas, rightShoulder, rightHip, checkPaint)
                        //발
                        drawLine(canvas, rightAnkle, rightHeel, checkPaint)
                        drawLine(canvas, rightAnkle, rightFootIndex, checkPaint)
                        drawLine(canvas, rightHeel, rightFootIndex, checkPaint)

                        drawLine(canvas, leftAnkle, leftHeel, checkPaint)
                        drawLine(canvas, leftAnkle, leftFootIndex, checkPaint)
                        drawLine(canvas, leftHeel, leftFootIndex, checkPaint)
                    }
                    else -> {
                        drawPoint(canvas, leftAnkle, whitePaint)
                        drawPoint(canvas, rightAnkle, whitePaint)
                        drawPoint(canvas, leftWrist, whitePaint)
                        drawPoint(canvas, rightWrist, whitePaint)
                        drawPoint(canvas, leftHip, whitePaint)
                        drawPoint(canvas, rightHip, whitePaint)
                        //Line------------------------------------------------------------
                        drawLine(canvas, rightKnee, rightAnkle, checkPaint)
                        drawLine(canvas, leftKnee, leftAnkle, checkPaint)
                        drawLine(canvas, leftShoulder, leftHip, checkPaint)
                        drawLine(canvas, leftHip, leftKnee, checkPaint)
                        drawLine(canvas, rightShoulder, rightHip, checkPaint)
                        drawLine(canvas, rightHip, rightKnee, checkPaint)
                        //몸통
                        drawLine(canvas, leftShoulder, rightShoulder, checkPaint)
                        drawLine(canvas, leftHip, rightHip, checkPaint)
                        //어깨 - 손목
                        drawLine(canvas, leftShoulder, leftElbow, checkPaint)
                        drawLine(canvas, leftElbow, leftWrist, checkPaint)

                        drawLine(canvas, rightShoulder, rightElbow, checkPaint)
                        drawLine(canvas, rightElbow, rightWrist, checkPaint)

                        //발
                        drawLine(canvas, rightAnkle, rightHeel, checkPaint)
                        drawLine(canvas, rightAnkle, rightFootIndex, checkPaint)
                        drawLine(canvas, rightHeel, rightFootIndex, checkPaint)

                        drawLine(canvas, leftAnkle, leftHeel, checkPaint)
                        drawLine(canvas, leftAnkle, leftFootIndex, checkPaint)
                        drawLine(canvas, leftHeel, leftFootIndex, checkPaint)
                    }
                }
            }
        }catch(e: Exception){
        }
        previosLeftElbowPoint = leftElbow
        previosRightElbowPoint = rightElbow
        previosLeftHipPoint = leftHip
        previosRightHipPoint = rightHip
        previosLeftWristPoint = leftWrist
        previosRightWristPoint = rightWrist
        previosLeftShoulderPoint = leftShoulder
        previosRightShoulderPoint = rightShoulder
    }
    private fun drawText(putText: String, canvas: Canvas, landmark: PoseLandmark, paint: Paint) {
        if(landmark.inFrameLikelihood > 0.97) {
            val point = landmark.position3D
            canvas.drawText(putText, translateX(point.x), translateY(point.y), paint)
        }
    }
    private fun drawPoint(canvas: Canvas, landmark: PoseLandmark, paint: Paint) {
        if(landmark.inFrameLikelihood > 0.97) {
            val point = landmark.position3D
            canvas.drawCircle(translateX(point.x), translateY(point.y), DOT_RADIUS, paint)
        }
    }

    internal fun drawLine(
        canvas: Canvas,
        startLandmark: PoseLandmark?,
        endLandmark: PoseLandmark?,
        paint: Paint
    ) {
        if((startLandmark!!.inFrameLikelihood > 0.97) && endLandmark!!.inFrameLikelihood > 0.97){
            val start = startLandmark!!.position3D
            val end = endLandmark!!.position3D
            canvas.drawLine(
                translateX(start.x),
                translateY(start.y),
                translateX(end.x),
                translateY(end.y),
                paint
            )
        }
    }
    fun updatePaintColor(checkState:String, checkList : ArrayList<MutableMap<String, Boolean>>):Boolean{
        //동작 10개중 틀린개수 4개이상 확인시 피드백
        var count = 0
        for(check in checkList){
            if(check[checkState]==false) count++
        }
        return count >= 4
    }
    fun stableData(queueData : Queue<Array<Float>>, xData : Float, yData: Float) : Array<Float>{
        var stableX = 0f
        var stableY = 0f
        queueData.add(arrayOf(xData, yData))
        if(queueData.size>3){
            queueData.poll()
            for(data in queueData){
                stableX += data[0]
                stableY += data[1]
            }
            stableX /= 3
            stableY /= 3
        }
        return arrayOf(stableX, stableY)
    }
    fun checkMoving(
        pointA: PoseLandmark?,
        pointB: PoseLandmark?,
        pointC: PoseLandmark?,
        pointD: PoseLandmark?,
        pointE: PoseLandmark?,
        pointF: PoseLandmark?,
        pointG: PoseLandmark?,
        pointH: PoseLandmark?,
        previosPointA: PoseLandmark?,
        previosPointB: PoseLandmark?,
        previosPointC: PoseLandmark?,
        previosPointD: PoseLandmark?,
        previosPointE: PoseLandmark?,
        previosPointF: PoseLandmark?,
        previosPointG: PoseLandmark?,
        previosPointH: PoseLandmark?,
    ): Boolean {
        return (catchMoving(pointA, previosPointA) &&
                catchMoving(pointB, previosPointB) &&
                catchMoving(pointC, previosPointC) &&
                catchMoving(pointD, previosPointD) &&
                catchMoving(pointE, previosPointE) &&
                catchMoving(pointF, previosPointF) &&
                catchMoving(pointG, previosPointG) &&
                catchMoving(pointH, previosPointH) )
    }
    fun checkMoving(
        pointA: PoseLandmark?,
        pointB: PoseLandmark?,
        pointC: PoseLandmark?,
        pointD: PoseLandmark?,
        previosPointA: PoseLandmark?,
        previosPointB: PoseLandmark?,
        previosPointC: PoseLandmark?,
        previosPointD: PoseLandmark?
    ): Boolean {
        return (catchMoving(pointA, previosPointA) &&
                catchMoving(pointB, previosPointB) &&
                catchMoving(pointC, previosPointC) &&
                catchMoving(pointD, previosPointD) )
    }
    fun catchMoving(now: PoseLandmark?, previos: PoseLandmark?) :Boolean{
        return( (now!!.position3D.x > previos!!.position3D.x * 0.98) &&
                (now!!.position3D.x < previos!!.position3D.x * 1.02) &&
                (now!!.position3D.y > previos!!.position3D.y * 0.98) &&
                (now!!.position3D.y < previos!!.position3D.y * 1.02))
    }
    internal fun drawLine(
        canvas: Canvas,
        startLandmark: PoseLandmark?,
        endLandmark: PoseLandmark?,
        paint: Paint,
        startStableData : Array<Float>,
        endStableData : Array<Float>
    ) {
        if((startLandmark!!.inFrameLikelihood > 0.97) && endLandmark!!.inFrameLikelihood > 0.97){
            canvas.drawLine(
                translateX(startStableData[0]),
                translateY(startStableData[1]),
                translateX(endStableData[0]),
                translateY(endStableData[1]),
                paint
            )
        }
    }
    companion object {
        private const val DOT_RADIUS = 13.0f
        private const val STROKE_WIDTH = 7.0f
        var stableLeftShoulder : Queue<Array<Float>> = LinkedList()
        var stableRightShoulder  : Queue<Array<Float>> = LinkedList()
        var stableLeftElbow  : Queue<Array<Float>> = LinkedList()
        var stableRightElbow  : Queue<Array<Float>> = LinkedList()
        var stableLeftWrist  : Queue<Array<Float>> = LinkedList()
        var stableRightWrist  : Queue<Array<Float>> = LinkedList()
        var stableLeftHip : Queue<Array<Float>> = LinkedList()
        var stableRightHip  : Queue<Array<Float>> = LinkedList()
        var stableLeftKnee  : Queue<Array<Float>> = LinkedList()
        var stableRightKnee  : Queue<Array<Float>> = LinkedList()
        var stableLeftAnkle  : Queue<Array<Float>> = LinkedList()
        var stableRightAnkle  : Queue<Array<Float>> = LinkedList()
        var stableRightHeel  : Queue<Array<Float>> = LinkedList()
        var stableLeftHeel  : Queue<Array<Float>> = LinkedList()
        var stableRightFootIndex  : Queue<Array<Float>> = LinkedList()
        var stableLeftFootIndex  : Queue<Array<Float>> = LinkedList()
        lateinit var previosLeftElbowPoint: PoseLandmark
        lateinit var previosRightElbowPoint: PoseLandmark
        lateinit var previosLeftHipPoint: PoseLandmark
        lateinit var previosRightHipPoint: PoseLandmark
        lateinit var previosLeftWristPoint: PoseLandmark
        lateinit var previosRightWristPoint: PoseLandmark
        lateinit var previosLeftShoulderPoint: PoseLandmark
        lateinit var previosRightShoulderPoint: PoseLandmark
    }
}
