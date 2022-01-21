package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL
import com.hnh.poseengine.logic.classification.PoseClassifierProcessor
import com.hnh.poseengine.logic.EXERCISE_INFORMATION.MODE_FREE
import com.hnh.poseengine.logic.EXERCISE_INFORMATION.MODE_SELECT_SET
import com.hnh.poseengine.logic.EXERCISE_INFORMATION.MODE_SET
import kotlin.collections.HashMap

/**
 * Created by hyerim on 2021/09/24...
 */

class PoseProcess(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) {

    companion object {
    }
    class DeadLift(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checklist = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checklist[DEAD_LIFT.FOOT_DISTANCE] = getPoseState.getFootToShoulderDistance() > 0

            //Down
            if (getPoseState.getKneetoWristPointY() < 0) {
                checklist[DEAD_LIFT.HIP_ANGLE_DOWN] = getPoseState.getHipAngle() > 150
//                checklist["BarWrist"] = getposestate.getKneetoWristPointZ() <1.5 ||
//                        getposestate.getKneetoWristPointZ()> 2.5
                checklist[DEAD_LIFT.BAR_WRIST] =
                    -getPoseState.getShoulderPointZ() + getPoseState.getWristPointZ() < -150
            }
            //Up
            else {
                checklist[DEAD_LIFT.HIP_ANGLE_UP] = getPoseState.getHipAngle() > 160
                -getPoseState.getShoulderPointZ() + getPoseState.getWristPointZ() < -150
//                checklist["BarWrist"] = getposestate.getKneetoWristPointZ() <0.7 ||
//                        getposestate.getKneetoWristPointZ()> 1.5
            }
            return checklist
        }
        override fun checkPose(pose: HHPose, debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            return checkList
        }
        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.DEAD_LIFT, EXERCISE_INFORMATION.FULL)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>){

        }
    }

    class Squat(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.LOWER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.MID_TIME
        }
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            if(!downFlag) downFlag = getPoseState.getKneeAngle3D() < 90
            checkList[SQUAT.FOOT_TO_SHOULDER_DISTANCE] = ((getPoseState.getFootToShoulderDistance() > 0) &&
                    (getPoseState.getFootToShoulderDistance() > 0))
            checkList[SQUAT.KNEE_POINT] = (getPoseState.getFootToKnee3D() < 23.5)
            if (getPoseState.getKneeAngle3D() < 100) {
                //무릎각, 발목각 차이
                checkList[SQUAT.KNEE_DISTANCE] = (getPoseState.getKneeToAnkle3D() < 13)//7.5)
                //무릎과 발목의 거리 데이터
//                checklist["KneeDistance"] = ((getposestate.getKneeDistance() > 1.1) &&
//                        (getposestate.getKneeDistance() < 1.65))
                //무릎 앞쪽으로 튀어나오는 정도
//                checklist["KneePoint"] = ((getposestate.getKneetoAnklePoint() > 1.1) ||
//                            (getposestate.getKneetoAnklePoint() < -3))
                //무릎 발목 발가락 삼각형의 무릎밖쪽 각도 데이터
            } else {
                checkList[SQUAT.KNEE_DISTANCE] = true
            }
            checkList[SQUAT.HIP_ANGLE] = getPoseState.getHipAngle3D() > 85


            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[SQUAT.KNEE_ANGLE_DOWN] = downFlag
                downFlag = false
            }

            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
//            checklist += getPoseState.checkFrame(true) + "\n"
            checkList += "엉덩이:$getPoseState.getFootToKnee3D()\n"
            checkList += "무릎거리:$getPoseState.getKneeToAnkle3D()\n"
            checkList += "무릎:$getPoseState.getKneeAngle3D()\n"
            checkList += "허리:$getPoseState.getHipAngle3D()\n"

            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.SQUAT, EXERCISE_INFORMATION.LOWER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }
        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            //Up, Down Flag Event (Logic)
            if((checkContracRelax[SQUAT.KNEE_ANGLE_DOWN] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.KNEE_DEGREE_90)
                checkContracRelax.clear()
            }

            if (wrongPoseCheck(SQUAT.FOOT_TO_SHOULDER_DISTANCE, checkListQueue) &&
                !checkListSound.containsKey(SQUAT.FOOT_TO_SHOULDER_DISTANCE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SPREAD_SHOULDER)
                checkListSound[SQUAT.FOOT_TO_SHOULDER_DISTANCE]=true
            }
            if (wrongPoseCheck(SQUAT.KNEE_POINT, checkListQueue) &&
                !checkListSound.containsKey(SQUAT.KNEE_POINT)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.HIP)
                checkListSound[SQUAT.KNEE_POINT]=true
            }
            if (wrongPoseCheck(SQUAT.KNEE_DISTANCE, checkListQueue) &&
                !checkListSound.containsKey(SQUAT.KNEE_DISTANCE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.KNEES_TOES)
                checkListSound[SQUAT.KNEE_DISTANCE]=true
            }
            if (wrongPoseCheck(SQUAT.HIP_ANGLE, checkListQueue) &&
                !checkListSound.containsKey(SQUAT.HIP_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.WAIST)
                checkListSound[SQUAT.HIP_ANGLE]=true
            }
        }
    }
    class SquatAI(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exersice = Pair("SquatAI", EXERCISE_INFORMATION.LOWER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exersice)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
        }
    }
    class  SideLateralRaise(val osal: OSAL, val exerciseGlobal: ExerciseGlobal):PoseProcessInterface{
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.LONG_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)

            if (!upFlag) upFlag = getPoseState.getShoulderAngle3D() < 25
            if (!downFlag) downFlag = getPoseState.getShoulderAngle3D() > 95

            if (getPoseState.getShoulderAngle3D()>50)
                checkList[SIDE_LATERAL_RAISE.WRIST_3D] =
                    (getPoseState.getRightShoulderToElbowToShoulderAngle3D() -
                            getPoseState.getShoulderAngle3D() > 60
                            && getPoseState.getLeftShoulderToElbowToShoulderAngle3D() -
                            getPoseState.getShoulderAngle3D() > 60)
            else checkList[SIDE_LATERAL_RAISE.WRIST_3D] = true
            checkList[SIDE_LATERAL_RAISE.SHOULDER_TO_WRIST] =
                (getPoseState.getRightWristToShuolderPointY() > 0
                        && getPoseState.getLeftWristToShuolderPointY() > 0)
            //팔꿈치가 손목보다 높으면 PASS 아닐때만 검사
            if(getPoseState.getWrisTtoElbowPointY()>0)
                checkList[SIDE_LATERAL_RAISE.ELBOW_ANGLE] = true
            else
                checkList[SIDE_LATERAL_RAISE.ELBOW_ANGLE] = getPoseState.getElbowAngle3D() > 145

            checkList[SIDE_LATERAL_RAISE.HIP_ANGLE] = getPoseState.getHipAngle3D() > 140

            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
                checkContracRelax[SIDE_LATERAL_RAISE.ELBOW_ANGLE_DOWN] = downFlag
                downFlag = false
            }
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[SIDE_LATERAL_RAISE.ELBOW_ANGLE_UP] = upFlag
                upFlag = false
            }

            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checklist = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checklist += "어깨:$getPoseState.getShoulderAngle3D()\n"
            checklist += "전면어깨:${getPoseState.getRightShoulderToElbowToShoulderAngle3D() - getPoseState.getShoulderAngle3D()}\n"
            checklist += "팔꿈치:$getPoseState.getElbowAngle3D()\n"
            checklist += "힙:$getPoseState.getHipAngle3D()\n"
            return checklist
        }
        //
        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.SIDE_LATERAL_RAISE, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>){

            //Up, Down Flag Event (Logic)
            if((checkContracRelax[SIDE_LATERAL_RAISE.ELBOW_ANGLE_UP] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.RELAXATION)
                checkContracRelax.clear()
            }
            if((checkContracRelax[SIDE_LATERAL_RAISE.ELBOW_ANGLE_DOWN] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SHRINK)
                checkContracRelax.clear()
            }

            if(wrongPoseCheck(SIDE_LATERAL_RAISE.WRIST_3D,checkListQueue) &&
                !checkListSound.containsKey(SIDE_LATERAL_RAISE.WRIST_3D)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SIDE_HANDS)
                checkListSound[SIDE_LATERAL_RAISE.WRIST_3D]=true
            }
            if(wrongPoseCheck(SIDE_LATERAL_RAISE.SHOULDER_TO_WRIST ,checkListQueue) &&
                !checkListSound.containsKey(SIDE_LATERAL_RAISE.SHOULDER_TO_WRIST)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SHOULDER_OVER)
                checkListSound[SIDE_LATERAL_RAISE.SHOULDER_TO_WRIST]=true
            }
            if(wrongPoseCheck(SIDE_LATERAL_RAISE.ELBOW_ANGLE,checkListQueue) &&
                !checkListSound.containsKey(SIDE_LATERAL_RAISE.ELBOW_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.ARMS_STRAIGHT)
                checkListSound[SIDE_LATERAL_RAISE.ELBOW_ANGLE]=true
            }
            if(wrongPoseCheck(SIDE_LATERAL_RAISE.HIP_ANGLE,checkListQueue) &&
                !checkListSound.containsKey(SIDE_LATERAL_RAISE.HIP_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.WAIST)
                checkListSound[SIDE_LATERAL_RAISE.HIP_ANGLE]=true
            }
        }
    }

    class ShoulderPress(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.MID_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)

            if (!upFlag) upFlag = getPoseState.getElbowAngle3D() > 155
            if (!downFlag) downFlag = getPoseState.getWristToEar() < 20

            if(getPoseState.getShoulderAngle3D()<100)
                checkList[SHOULDER_PRESS.ELBOW_ANGLE] = ((getPoseState.getElbowAngle3D() < 110)
                        && (getPoseState.getElbowAngle3D() > 60))
            //팔을 들어올릴때 getShoulderToElbowToShoulderAngle3D의 각도 변동으로
            //어깨각도를 합산하여 동작에 앞쪽으로 빠지는지 판단.
            checkList[SHOULDER_PRESS.ELBOW_DOWN] = getPoseState.getShoulderAngle3D() > 70
            if((getPoseState.getShoulderAngle3D() < 120)
                && (getPoseState.getShoulderAngle3D() > 75)
                &&(getPoseState.getCenterAngle() > 80)
                &&(getPoseState.getCenterAngle() < 100)) {
                checkList[SHOULDER_PRESS.WRIST_3D] =
                    (getPoseState.getRightShoulderToElbowToShoulderAngle3D() > 125 &&
                            getPoseState.getLeftShoulderToElbowToShoulderAngle3D() > 125)
            }
            else checkList[SHOULDER_PRESS.WRIST_3D] = true
            if(getPoseState.getElbowAngle3D() > 145){
                checkList[SHOULDER_PRESS.ELBOW_FRONT] = getPoseState.getShoulderToWristAngle3D() > 165
            }
            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
                checkContracRelax[SHOULDER_PRESS.DOWN_WRIST] = downFlag
                downFlag = false
            }
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[SHOULDER_PRESS.ELBOW_UP] = upFlag
                upFlag = false
            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checklist = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checklist += "팔꿈치:$getPoseState.getElbowAngle3D()\n"
            checklist += "어깨:$getPoseState.getShoulderAngle3D()\n"
            checklist += "앞쪽어깨:$getPoseState.getShoulderToWristAngle3D()\n"
            checklist += "센터 :$getPoseState.getRightShoulderToElbowToShoulderAngle3D()\n"
            return checklist
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.SHOULDER_PRESS, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            //Up, Down Flag Event (Logic)
            if((checkContracRelax[SHOULDER_PRESS.DOWN_WRIST] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.DOWN_WRIST)
                checkContracRelax.clear()
            }
            if((checkContracRelax[SHOULDER_PRESS.ELBOW_UP] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.UP_WRIST)
                checkContracRelax.clear()
            }
            if (wrongPoseCheck(SHOULDER_PRESS.ELBOW_FRONT, checkListQueue) &&
                !checkListSound.containsKey(SHOULDER_PRESS.ELBOW_FRONT)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.UP_TO_HEAD_DUMBBELL)
                checkListSound[SHOULDER_PRESS.ELBOW_FRONT]=true
            }
            if (wrongPoseCheck(SHOULDER_PRESS.WRIST_3D, checkListQueue) &&
                !checkListSound.containsKey(SHOULDER_PRESS.WRIST_3D)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SIDE_HANDS)
                checkListSound[SHOULDER_PRESS.WRIST_3D]=true
            }
            if (wrongPoseCheck(SHOULDER_PRESS.ELBOW_ANGLE, checkListQueue) &&
                !checkListSound.containsKey(SHOULDER_PRESS.ELBOW_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.HEAD_DUMBBELL)
                checkListSound[SHOULDER_PRESS.ELBOW_ANGLE]=true
            }
            if (wrongPoseCheck(SHOULDER_PRESS.ELBOW_DOWN, checkListQueue) &&
                !checkListSound.containsKey(SHOULDER_PRESS.ELBOW_DOWN)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.UP_ELBOW)
                checkListSound[SHOULDER_PRESS.ELBOW_DOWN]=true
            }
        }
    }
    class LatPullDown(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            if (!downFlag) downFlag = getPoseState.getWristToShoulder() > -10
            if (!upFlag) upFlag = getPoseState.getElbowAngle3D() > 140
            checkList[LAT_PULL_DOWN.HIP_ANGLE] = getPoseState.getShoulderToHipFrontVertical3D() <65
            checkList[LAT_PULL_DOWN.WRIST_BAR] =
                getPoseState.getWristDistance() > getPoseState.getShoulderDistance3D() * 1.8
            if (getPoseState.getElbowAngle3D() < 100) {
//            checkList["ShoulderPacking"] = getPoseState.getShoulderVertical() > 65
//                (getPoseState.getShoulderToElbowToElbowAngle3D() -
//                    getPoseState.getShoulderAngle3D() ) > 50
            }
            checkList[LAT_PULL_DOWN.ELBOW_VERTICAL] = (getPoseState.getElbowToWristBackVertical3D() > 55)
            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
                checkContracRelax[LAT_PULL_DOWN.ELBOW_UP] = upFlag
                upFlag = false
            }
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[LAT_PULL_DOWN.WRIST_DOWN] = downFlag
                downFlag = false
            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checkList += "팔꿈치:$getPoseState.getElbowAngle3D()\n"
            checkList += "팔꿈치 직선:$getPoseState.getElbowToWristBackVertical3D()\n"
            checkList += "허리:$getPoseState.getShoulderToHipFrontVertical3D()\n"

            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.LAT_PULL_DOWN, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            //Up, Down Flag Event (Logic)
            if((checkContracRelax[LAT_PULL_DOWN.WRIST_DOWN] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.DOWN_BAR)
                checkContracRelax.clear()
            }
            if((checkContracRelax[LAT_PULL_DOWN.ELBOW_UP] == false)){
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.ELBOW_STRAIGHT)
                checkContracRelax.clear()
            }
            if (wrongPoseCheck(LAT_PULL_DOWN.ELBOW_VERTICAL, checkListQueue) &&
                !checkListSound.containsKey(LAT_PULL_DOWN.ELBOW_VERTICAL)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.ELBOW_VERTICAL)
                checkListSound[LAT_PULL_DOWN.ELBOW_VERTICAL]=true
            }

            if (wrongPoseCheck(LAT_PULL_DOWN.WRIST_BAR, checkListQueue) &&
                !checkListSound.containsKey(LAT_PULL_DOWN.WRIST_BAR)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.LONG_BAR)
                checkListSound[LAT_PULL_DOWN.WRIST_BAR]=true
            }
            if (wrongPoseCheck(LAT_PULL_DOWN.HIP_ANGLE, checkListQueue) &&
                !checkListSound.containsKey(LAT_PULL_DOWN.HIP_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FIX_HIP)
                checkListSound[LAT_PULL_DOWN.HIP_ANGLE]=true
            }
            //견갑 움직임 연구후 추가예정
//            if (wrongPoseCheck("ShoulderPacking", checkList)) {
//                poseResult = false
//            }
        }
    }
    class CableLow(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.MID_TIME
        }
        var upFlag = false
        var downFlag = false

        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checklist = HashMap<String, Boolean>()
//            val getposestate = PoseUtile(pose)
//            if(getposestate.stophand()){
//                checklist["Wrist3D"] = true
//                checklist["ShoulderToWrist"] = true
//            }else {
//                //들어올린상태
//                if(updownstate == "down") {
//                    if(!upFlag) upFlag = getposestate.getElbowAngleNew3D()>160
//                    //내린상태
//                }else{
//                    if(!downflag)downflag = getposestate.getwristtoear()<0
//
//                    if(getposestate.getShoulderAngleNew3D()>110)
//                        checklist["SPElbowAngle"] = getposestate.getElbowAngleNew3D() < 130
//                }
//                checklist["Wrist3D"] = getposestate.getShoulderAngleNew3D() > 80
//            }
//            if((preupdownstate == "up")&& (updownstate == "down")){
//                checklist["DownWrist"] = downflag
//                downflag = false
//            }
//            if((preupdownstate == "down")&& (updownstate == "up")){
//                checklist["UpElbow"] = upFlag
//                upFlag = false
//            }
            return checklist
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checklist = ""
            val getposestate = PoseUtil(osal, exerciseGlobal, pose)
//            checklist += String.format("Shoulder3D:%.2f", getposestate.getShoulderAngleNew3D())+"\n"
//            checklist += String.format("Elbow:%.2f", getposestate.getElbowAngleNew3D())+"\n"
//            checklist += String.format("Elbow:%.2f", getposestate.getwristtoear())+"\n"
//            checklist += String.format("upFlag : $upFlag")+"\n"
//            checklist += String.format("downflag : $downflag")+"\n"

            return checklist
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.CABLE_ROW, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
//            if(PoseLogic.checklist["DownWrist"] == false) {
//                downflag = true
//                poseResult = false
//                TTSUtil.getInstance(_context).speakText("덤벨을 귀옆까지 내려주세요")
//            }
//            if(PoseLogic.checklist["UpElbow"] == false) {
//                upFlag = true
//                poseResult = false
//                TTSUtil.getInstance(_context).speakText("팔꿈치를 쭉펴주세요")
//            }
//            if(wrongPoseCheck("Wrist3D",checklist)) {
//                poseResult = false
//                TTSUtil.getInstance(_context).speakText("손을 옆으로만 움직이세요")
//            }
//            if(wrongPoseCheck("SPElbowAngle",checklist)) {
//                poseResult = false
//                TTSUtil.getInstance(_context).speakText("덤벨을 귀쪽으로 붙이세요")
//            }
        }
    }
    class Lunge(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.LOWER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.MID_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
//            val kneeToAnkle = (getPoseState.getKneeSideXFront()*100 / getPoseState.getCenterAngle()).roundToInt()
            if (milsPoseState.first == "down"){
                if (!downFlag) downFlag = getPoseState.getKneeAngleFront3D() < 120
                //무릎 전면 돌출 체크
                checkList[LUNGE.KNEE_POINT] = getPoseState.getFootToKneeFrontVertical3D() < 66
                //무릎방향 체크 TestVersion
//                if((getPoseState.getKneeAngleFront3D()<90)&&
//                    (getPoseState.getCenterAngle()>85) &&
//                    (getPoseState.getCenterAngle()<95)) {
//                    checkList[LUNGE.KNEE_TO_CENTER] = ((kneeToAnkle > 60) && (kneeToAnkle < 110))
//                }
            }
//            checkList["Pelvis"] = ((getPoseState.getPelvisAngle3D() < 120) &&
//                    (getPoseState.getPelvisAngle3D() > 80))
//            checkList[LUNGE.FOOT_TO_PELVIS_DISTANCE] =
//                ((getPoseState.getPelvisDistance()*1.8 > getPoseState.getKneeDistance()) &&
//                        (getPoseState.getPelvisDistance()*0.3  < getPoseState.getKneeDistance()))
            if((getPoseState.getCenterAngle()>85) &&
                (getPoseState.getCenterAngle()<95)){
                checkList[LUNGE.FOOT_STRAIGHT] =(( getPoseState.getPelvisFrontAngle3D()>80)
                        && ( getPoseState.getPelvisFrontAngle3D()<102))
            }
            checkList[LUNGE.HIP_ANGLE] = getPoseState.getHipAngle3D() > 70
//            checkList["FootDistance"] = (getPoseState.getFootDistance() > getPoseState.getShoulderDistance()*0.1
//                    && getPoseState.getFootDistance() < getPoseState.getShoulderDistance()*0.7)
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[LUNGE.KNEE_ANGLE_DOWN] = downFlag
                downFlag = false
            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checkList += getPoseState.getFrontKneeCheck() + "\n"
            checkList += "FootA:$getPoseState.getFootToKneeFrontVertical3D()\n"
            checkList += "FrontKA:$getPoseState.getKneeAngleFront3D()\n"
            checkList += "골반:$getPoseState.getPelvisFrontAngle3D()\n"
//            checkList += String.format("SideCenter:%d", (getPoseState.getKneeSideXFront()*100 /
//                    getPoseState.getCenterAngle()).roundToInt()) + "\n"
            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.LUNGE, EXERCISE_INFORMATION.LOWER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            if(checkContracRelax[LUNGE.KNEE_ANGLE_DOWN] == false) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.KNEE_DEGREE_90)
                checkContracRelax.clear()
            }
            if (wrongPoseCheck(LUNGE.FOOT_TO_PELVIS_DISTANCE, checkListQueue)&&
                !checkListSound.containsKey(LUNGE.FOOT_TO_PELVIS_DISTANCE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FOOT_PELVIS_DISTANCE)
                checkListSound[LUNGE.FOOT_TO_PELVIS_DISTANCE]=true
            }
            if (wrongPoseCheck(LUNGE.KNEE_POINT, checkListQueue)&&
                !checkListSound.containsKey(LUNGE.KNEE_POINT)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.HIP_VERTICAL)
                checkListSound[LUNGE.KNEE_POINT]=true
            }
            if (wrongPoseCheck(LUNGE.KNEE_TO_CENTER, checkListQueue)&&
                !checkListSound.containsKey(LUNGE.KNEE_TO_CENTER)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.KNEE_FOOT_ANGLE)
                checkListSound[LUNGE.KNEE_TO_CENTER]=true
            }
            if (wrongPoseCheck(LUNGE.HIP_ANGLE, checkListQueue)&&
                !checkListSound.containsKey(LUNGE.HIP_ANGLE)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.HIP)
                checkListSound[LUNGE.HIP_ANGLE]=true
            }
            if (wrongPoseCheck(LUNGE.FOOT_STRAIGHT, checkListQueue)&&
                !checkListSound.containsKey(LUNGE.FOOT_STRAIGHT)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FOOT_STRAIGHT)
                checkListSound[LUNGE.FOOT_STRAIGHT]=true
            }
        }
    }
    class CablePushDown(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        companion object{
            val shouderQueue : ArrayList<Double> = ArrayList<Double>()
            val hipQueue : ArrayList<Double> = ArrayList<Double>()
            var sumShoulder =0.0
            var sumHip = 0.0
        }
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)

            shouderQueue.add(getPoseState.getShoulderAngleFront3D())
            hipQueue.add(getPoseState.getHipAngleFront3D())
            if(shouderQueue.size>10){
                sumShoulder = 0.0
                shouderQueue.removeFirst()
                for(angle in shouderQueue){
                    sumShoulder += angle
                }
                checkList[CABLE_PUSH_DOWN.ELBOW_FIX] =
                    (getPoseState.getShoulderAngleFront3D() - sumShoulder/10 < 7) &&
                            (getPoseState.getShoulderAngleFront3D() - sumShoulder/10 > -7)
            }
            if(hipQueue.size>10){
                sumHip = 0.0
                hipQueue.removeFirst()
                for(angle in hipQueue){
                    sumHip += angle
                }
                checkList[CABLE_PUSH_DOWN.HIP_FIX] =
                    (getPoseState.getHipAngleFront3D() - sumHip/10 < 7) &&
                            (getPoseState.getHipAngleFront3D() - sumHip/10 > -7)
            }
            //commit
            //3D 보다 2D가 인식률 더 좋음 -> 측면 45도 까지만 테스트
            //팔 내린상태 (수축)
            if (!upFlag) upFlag = getPoseState.getElbowAngleFront() > 150
            //팔 올린 상태 (이완)
            if (!downFlag) downFlag = getPoseState.getElbowAngleFront() < 85
            //이완
            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
                checkContracRelax[CABLE_PUSH_DOWN.ELBOW_ANGLE_DOWN] = downFlag
                downFlag = false
            }
            //수축
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[CABLE_PUSH_DOWN.ELBOW_ANGLE_UP] = upFlag
                upFlag = false
            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checkList +=getPoseState.getFrontShoulderCheck()+ "\n"
            checkList += "ElbowA3D:$getPoseState.getElbowAngleFront()\n"
            checkList += "Elbow:${getPoseState.getShoulderAngleFront3D() - sumShoulder/10}\n"

            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.CABLE_PUSH_DOWN, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            if(checkContracRelax[CABLE_PUSH_DOWN.ELBOW_ANGLE_DOWN] == false) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.RELAXATION)
                checkContracRelax.clear()
            }
            if(checkContracRelax[CABLE_PUSH_DOWN.ELBOW_ANGLE_UP] == false) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SHRINK)
                checkContracRelax.clear()
            }
            if (wrongPoseCheck(CABLE_PUSH_DOWN.ELBOW_FIX, checkListQueue)&&
                !checkListSound.containsKey(CABLE_PUSH_DOWN.ELBOW_FIX)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FIX_ELBOW)
                checkListSound[CABLE_PUSH_DOWN.ELBOW_FIX]=true
            }
            if (wrongPoseCheck(CABLE_PUSH_DOWN.HIP_FIX, checkListQueue)&&
                !checkListSound.containsKey(CABLE_PUSH_DOWN.HIP_FIX)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FIX_HIP)
                checkListSound[CABLE_PUSH_DOWN.HIP_FIX]=true
            }
        }
    }
    class StandingBarbellCurl(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.UPPER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()
        companion object{
            val shouderQueue : ArrayList<Double> = ArrayList<Double>()
            val hipQueue : ArrayList<Double> = ArrayList<Double>()
            var sumShoulder =0.0
            var sumHip = 0.0
        }
        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)

            shouderQueue.add(getPoseState.getShoulderAngle())
            hipQueue.add(getPoseState.getHipAngle3D())
            if(shouderQueue.size>10){
                sumShoulder = 0.0
                shouderQueue.removeFirst()
                for(angle in shouderQueue){
                    sumShoulder += angle
                }
                checkList[STANDING_BARBELL_CURL.ELBOW_FIX] =
                    (getPoseState.getShoulderAngle() - sumShoulder/10 < 5) &&
                            (getPoseState.getShoulderAngle() - sumShoulder/10 > -5)
            }
            if(hipQueue.size>10){
                sumHip = 0.0
                hipQueue.removeFirst()
                for(angle in hipQueue){
                    sumHip += angle
                }
                checkList[STANDING_BARBELL_CURL.HIP_FIX] =
                    (getPoseState.getHipAngle3D() - sumHip/10 < 4) &&
                            (getPoseState.getHipAngle3D() - sumHip/10 > -4)
            }

            //팔 내린상태
            if (!upFlag) upFlag = getPoseState.getElbowAngle3D() > 135
            //팔 올린 상태
            if (!downFlag) downFlag = getPoseState.getElbowAngle3D() < 90
            //수축
            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
                checkContracRelax[STANDING_BARBELL_CURL.ELBOW_ANGLE_UP] = upFlag
                upFlag = false
            }
            //이완
            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
                checkContracRelax[STANDING_BARBELL_CURL.ELBOW_ANGLE_DOWN] = downFlag
                downFlag = false
            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
            checkList += "ElbowA2D:${getPoseState.getElbowAngle()}\n"
            checkList += "ElbowA3D:${getPoseState.getElbowAngle3D()}\n"
            checkList += "Elbow:${getPoseState.getShoulderAngle() - sumShoulder/10}\n"
            checkList += "Hip:${getPoseState.getHipAngle3D() - sumHip/10}\n"
            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.STANDING_BARBELL_CURL, EXERCISE_INFORMATION.UPPER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
            if(checkContracRelax[STANDING_BARBELL_CURL.ELBOW_ANGLE_DOWN] == false) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.SHRINK)
                checkContracRelax.clear()
            }
            if(checkContracRelax[STANDING_BARBELL_CURL.ELBOW_ANGLE_UP] == false) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.RELAXATION)
                checkContracRelax.clear()
            }

            if (wrongPoseCheck(STANDING_BARBELL_CURL.ELBOW_FIX, checkListQueue)&&
                !checkListSound.containsKey(STANDING_BARBELL_CURL.ELBOW_FIX)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FIX_ELBOW)
                checkListSound[STANDING_BARBELL_CURL.ELBOW_FIX]=true

            }
            if (wrongPoseCheck(STANDING_BARBELL_CURL.HIP_FIX, checkListQueue)&&
                !checkListSound.containsKey(STANDING_BARBELL_CURL.HIP_FIX)) {
                exerciseGlobal.poseResult = false
                osal.playSound(EXERCISE.FIX_HIP)
                checkListSound[STANDING_BARBELL_CURL.HIP_FIX]=true
            }
        }
    }
    class LegRaise(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) : PoseProcessInterface {
        init{
            exerciseGlobal.initExerciseType = EXERCISE_INFORMATION.LOWER
            exerciseGlobal.initExerciseDownTime = EXERCISE_INFORMATION.MID_TIME
            exerciseGlobal.initExerciseUpTime = EXERCISE_INFORMATION.MID_TIME
        }
        var upFlag = false
        var downFlag = false
        var checkContracRelax: HashMap<String, Boolean> = HashMap<String, Boolean>()

        override fun checkPose(pose: HHPose, milsPoseState: Pair<String, String>): HashMap<String, Boolean> {
            val checkList = HashMap<String, Boolean>()
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)
//
//            //팔 내린상태
//            if (!upFlag) upFlag = getPoseState.getElbowAngle3D() > 135
//            //팔 올린 상태
//            if (!downFlag) downFlag = getPoseState.getElbowAngle3D() < 90
//            //수축
//            if ((milsPoseState.second == "up") && (milsPoseState.first == "down")) {
//                checkContracRelax[LEG_RAISE.HIP_ANGLE_UP] = upFlag
//                upFlag = false
//            }
//            //이완
//            if ((milsPoseState.second == "down") && (milsPoseState.first == "up")) {
//                checkContracRelax[LEG_RAISE.HIP_ANGLE_DOWN] = downFlag
//                downFlag = false
//            }
            return checkList
        }

        override fun checkPose(pose: HHPose, Debugger: Boolean): String {
            var checkList = ""
            val getPoseState = PoseUtil(osal, exerciseGlobal, pose)

            return checkList
        }

        override fun classificationType(): PoseClassifierProcessor {
            val exercise = Pair(EXERCISE_LIST.LEG_RAISE, EXERCISE_INFORMATION.LOWER)
            return PoseClassifierProcessor(osal, exerciseGlobal, true, exercise)
        }

        override fun soundSet(checkListQueue: ArrayList<MutableMap<String, Boolean>>,
                              checkListSound: HashMap<String, Boolean>) {
//            if(checkContracRelax[LEG_RAISE.HIP_ANGLE_DOWN] == false) {
//                poseResult = false
//                ExerciseSoundUtil.getInstance(_context)!!.playSound(EXERCISE.SHRINK)
//                checkContracRelax.clear()
//            }
//            if(checkContracRelax[LEG_RAISE.HIP_ANGLE_UP] == false) {
//                poseResult = false
//                ExerciseSoundUtil.getInstance(_context)!!.playSound(EXERCISE.RELAXATION)
//                checkContracRelax.clear()
//            }

        }
    }
}
//
fun wrongPoseCheck(checkstate:String, checklist : ArrayList<MutableMap<String, Boolean>>):Boolean{
    //동작 10개중 틀린개수 4개이상 확인시 피드백
    var count = 0
    for(check in checklist){
        if(check[checkstate]==false) count++
    }
    return count >= 4
}
