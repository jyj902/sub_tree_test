package com.hnh.presentation.ui.posedetector

import android.Manifest
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.video.QualitySelector.QUALITY_LOWEST
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hnh.presentation.BuildConfig
import com.hnh.presentation.R
import com.hnh.presentation.base.BaseActivity
import com.hnh.presentation.databinding.ActivityPoseDetectorBinding
import com.hnh.presentation.logic.*
import com.hnh.presentation.ui.exercise.video.VideoDetailActivity
import com.hnh.presentation.util.*
//import com.hnh.presentation.util.ExerciseGlobal.Companion.exerciseMode
//import com.hnh.presentation.util.ExerciseGlobal.Companion.exerciseReps
//import com.hnh.presentation.util.ExerciseGlobal.Companion.exerciseSet
//import com.hnh.presentation.util.ExerciseGlobal.Companion.initGlobalObject
//import com.hnh.presentation.util.ExerciseGlobal.Companion.restTime
//import com.hnh.presentation.util.ExerciseGlobal.Companion.isStart
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.*


/**
 * Created by hyerim on 2021/09/24...
 */
@AndroidEntryPoint
class PoseDetectorActivity() :
    BaseActivity<ActivityPoseDetectorBinding, PoseDetectViewModel>(R.layout.activity_pose_detector),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {
    override val viewModel: PoseDetectViewModel by viewModels()
    lateinit var context: PoseDetectorActivity
    private lateinit var cameraExecutor: ExecutorService

    private var isLevel3 = true

    //media recorder 관련 변수
    private var isRecording = false
    private lateinit var videoCapture: VideoCapture<Recorder>
    private var activeRecording: ActiveRecording? = null

    //wear Send DataItems.
    private var mGeneratorExecutor: ScheduledExecutorService? = null
    private var isWearable = false

    //STT
    private lateinit var sttIntent: Intent
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    private val mainThreadExecutor by lazy { ContextCompat.getMainExecutor(this) }

    //video 주소
    private var videoPath = ""

    //운동 이름
    var exerciseID: String = ""
    var osal = AndroidOSAL()
    var exerciseGlobal = com.hnh.poseengine.logic.ExerciseGlobal(osal)

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stateFlow()

        exerciseID = intent.getStringExtra("poseId").toString()
        videoPath = intent.getStringExtra("videoPath").toString()

        //***스쿼트로 임시구현해둠 **/
        //이걸로 통일해두면 제가 바꾸겠습니다--
        exerciseGlobal.exerciseMode = PreferenceUtil.preferenceInstance(this).
        getExerciseSetting(exerciseID, EXERCISE_INFORMATION.SETTING_MODE) as Int
        exerciseGlobal.initExerciseSet = PreferenceUtil.preferenceInstance(this).
        getExerciseSetting(exerciseID, EXERCISE_INFORMATION.SETTING_SET) as Int
        exerciseGlobal.initExerciseReps = PreferenceUtil.preferenceInstance(this).
        getExerciseSetting(exerciseID,EXERCISE_INFORMATION.SETTING_REPS) as Int
        exerciseGlobal.initRestTime = PreferenceUtil.preferenceInstance(this).
        getExerciseSetting(exerciseID, EXERCISE_INFORMATION.SETTING_REST_TIME) as Int
        exerciseGlobal.initExerciseType = getExerciseType(exerciseID)
        if(exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_FREE){
            exerciseGlobal.initExerciseSet = 1
            exerciseGlobal.initExerciseReps = 999
        }

        //예외 처리 서버로부터 받아온 값 없을경우 종료
        if (!EXERCISE_LIST.constrainList(exerciseID)) {
            Toast.makeText(this, "Exercise Data Error", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        Log.d(TAG, "poseId : $exerciseID")

//        binding.tvInfo.bringToFront()
        setData = Pair(exerciseID, true)
        viewtext = exerciseID
        checkCameraVersion()

        init()
//        subscribeToLiveData()
        initView()
        getNodes()

        // Request permissions
        if (allPermissionsGranted()) {
            startCamera()
            STTUtil(applicationContext)
        } else ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )

        mGeneratorExecutor = ScheduledThreadPoolExecutor(1)

        cameraExecutor = Executors.newSingleThreadExecutor()

    }
    private fun stateFlow(){
        viewModel.infoResult.asLiveData().observe(this){ result ->
            Log.d("PoseDetectActivity","${result.exerciseFlag}${result.allCount}${result.failCount}${result.nowSets}")
            when (result.exerciseFlag) {
                1 -> { //운동중
                    binding.textviewInfoData.text =
                        "현재 세트 수 :${result.nowSets} \n운동 총 횟수 : ${result.allCount} \n실패횟수 : ${result.failCount} \n${result.pose} \n${result.speed}"
                    binding.textviewCount.text = "${result.successCount}/${result.defaultCount}"

                    //체크되어있을 경우 운동 녹화 시작 / 체크 버튼 비활성화
                    binding.checkboxRecording.isEnabled = false
                    if (result.allCount == 0 && binding.checkboxRecording.isChecked) {
                        onRecord(true)
                    }
                }
                0 -> { //휴식
                    binding.textviewInfoData.text = "휴식시간 ${result.restTime}초 지났습니다"
                    binding.textviewCount.text = "0/${result.defaultCount}"

                    //체크되어있을 경우 휴식 시작할 때, 운동 녹화 종료
                    binding.checkboxRecording.isEnabled = true
                    if (hasRecordingCamera() && (result.restTime == 1)) {
                        onRecord(false)
                    }

                }
                -1 -> { //테스트모드
                    binding.textviewInfoData.text = result.testMessage
                    binding.textviewCount.text = "${result.successCount}/${result.defaultCount}"
                }
            }

        }
        viewModel.count.asLiveData().observe(this){ exerciseCount ->

        }
        viewModel.isFinish.asLiveData().observe(this){ isFinish ->
            if (isFinish) {
                ExerciseSoundUtil.getInstance(this)!!.playBackgroundSound(EXERCISE.ENDING_SOUND)
                binding.animationView.visibility = View.VISIBLE
                binding.animationView.playAnimation()
                ProcessCameraProvider.getInstance(this).get().unbindAll()
                if (hasRecordingCamera() && binding.checkboxRecording.isChecked) {
                    onRecord(false)
                }
            }
        }
        viewModel.isRestTime10.asLiveData().observe(this){ isRest10 ->
            if (isRest10) {
                binding.animationViewRest.visibility = View.VISIBLE
                binding.animationViewRest.playAnimation()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.poseState.collect { poseState ->
                Log.d("StateFlow Pose :", poseState)
            }
        }

        viewModel.count.asLiveData().observe(this) {
                count -> Log.d("StateFlow Count", "$count")
        }
    }

    private fun getExerciseType(exerciseName : String):Int{
        return when(exerciseName){
            EXERCISE_LIST.DEAD_LIFT ->              EXERCISE_INFORMATION.FULL
            EXERCISE_LIST.SQUAT ->                  EXERCISE_INFORMATION.LOWER
            EXERCISE_LIST.SIDE_LATERAL_RAISE ->     EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.SHOULDER_PRESS ->         EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.CABLE_ROW ->              EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.LUNGE ->                  EXERCISE_INFORMATION.LOWER
            EXERCISE_LIST.LAT_PULL_DOWN ->          EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.CABLE_PUSH_DOWN ->        EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.STANDING_BARBELL_CURL ->  EXERCISE_INFORMATION.UPPER
            else -> {EXERCISE_INFORMATION.FULL}
        }
    }
    private fun checkCameraVersion() {
        val manager = getSystemService(CAMERA_SERVICE) as CameraManager

        var oneLevel3 = false
        for (cameraId in manager.cameraIdList) {
            val characteristics = manager.getCameraCharacteristics(cameraId)
            Log.d(
                "Img",
                "INFO_SUPPORTED_HARDWARE_LEVEL " + characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
            )
            if (3 == characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)) {
                oneLevel3 = true
            }
        }
        isLevel3 = oneLevel3

        setRecordingButton()
        binding.checkboxRecording.isChecked = PreferenceUtil.preferenceInstance(instance).isRecord
    }

    private fun setRecordingButton() {
        if (hasRecordingCamera()) {
            binding.viewCheckbox.visibility = View.VISIBLE
            isRecording = false
        } else {
            binding.viewCheckbox.visibility = View.GONE
            isRecording = false
        }
    }

    /** init method */
    private fun init() {
        graphicOverlay = binding.graphicOverlay

        sttIntent = Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE)
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        setListener()
    }

    private fun subscribeToLiveData() {
//        viewModel.infoSet.observe(this, Observer { _ ->
//            viewModel.analiser =
//                PoseImageAnalyzer(PoseLogic(viewModel), viewModel, exerciseID, graphicOverlay)
////            startCamera()
//        })

//        var defaultCount = 0
//        viewModel.infoResult.observe(this, Observer<ExercisePose> { result ->
//
//            when (result.exerciseFlag) {
//                1 -> { //운동중
//                    defaultCount = result.defaultCount
//                    binding.textviewInfoData.text =
//                        "현재 세트 수 :${result.nowSets} \n운동 총 횟수 : ${result.allCount} \n실패횟수 : ${result.failCount} \n${result.pose} \n${result.speed}"
//                    binding.textviewCount.text = "${result.successCount}/${result.defaultCount}"
//
//                    //체크되어있을 경우 운동 녹화 시작 / 체크 버튼 비활성화
//                    binding.checkboxRecording.isEnabled = false
//                    if (result.allCount == 0 && binding.checkboxRecording.isChecked) {
//                        onRecord(true)
//                    }
//                }
//                0 -> { //휴식
//                    binding.textviewInfoData.text = "휴식시간 ${result.restTime}초 지났습니다"
//                    binding.textviewCount.text = "0/$defaultCount"
//
//                    //체크되어있을 경우 휴식 시작할 때, 운동 녹화 종료
//                    binding.checkboxRecording.isEnabled = true
//                    if (hasRecordingCamera() && (result.restTime == 1)) {
//                        onRecord(false)
//                    }
//
//                }
//                -1 -> { //테스트모드
//                    binding.textviewInfoData.text = result.testMessage
//                    binding.textviewCount.text = "${result.successCount}/${result.defaultCount}"
//                }
//            }
//
//
//        })
//
//        viewModel.count.observe(this, Observer<Int> { exerciseCount ->
//            Log.d("testtest", "count : $exerciseCount")
//            if (isWearable) {
//                val putDataMapRequest = PutDataMapRequest.create(COUNT_PATH).apply {
//                    dataMap.putInt(COUNT_KEY, exerciseCount)
//                }
//                val request = putDataMapRequest.asPutDataRequest().apply {
//                    setUrgent()
//                }
//                Wearable.getDataClient(this).putDataItem(request)
//            }
//        })
//
//        viewModel.isFinish.observe(this, Observer { isFinish ->
//            if (isFinish) {
//                ExerciseSoundUtil.getInstance(this)!!.playBackgroundSound(EXERCISE.ENDING_SOUND)
//                binding.animationView.visibility = View.VISIBLE
//                binding.animationView.playAnimation()
//                ProcessCameraProvider.getInstance(this).get().unbindAll()
//                if (hasRecordingCamera() && binding.checkboxRecording.isChecked) {
//                    onRecord(false)
//                }
//            }
//
//        })
//
//        viewModel.isRestTime10.observe(this, Observer { isRest ->
//            if (isRest) {
//                binding.animationViewRest.visibility = View.VISIBLE
//                binding.animationViewRest.playAnimation()
//            }
//        })

    }

    private fun initView() {
        //!!!!!!!!!필요할경우 layout visible 처리하면 됨!!!!!!!
        binding.layoutDevelopButton.visibility = View.INVISIBLE
        //DEBUG MODE 디버그 모드
        if (BuildConfig.IS_DEBUG) binding.layoutDevelopButton.visibility = View.VISIBLE
        binding.buttonVideo.setOnClickListener {
            if (videoPath.length > 1) {
                var intent = Intent(this, VideoDetailActivity::class.java)
                intent.putExtra("VIDEO_PATH", videoPath)
                startActivity(intent)
            } else {
                makeNoVideoDialog()
            }
        }

        binding.buttonCameraChanged.setOnClickListener {
            if (cameraState == CameraSelector.DEFAULT_BACK_CAMERA) {
                cameraState = CameraSelector.DEFAULT_FRONT_CAMERA
                startCamera()
            } else {
                cameraState = CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
            setRecordingButton()
        }
        binding.buttonChangeInfoType.setOnClickListener {
            buttonSet = !buttonSet
            viewModel.setIntfo(setData)
            exerciseGlobal.buttonSet = buttonSet
            binding.buttonChangeInfoType.text =
                if (buttonSet) "data"
                else "info"

            setRecordingButton()
        }

        binding.buttonGraphicChanged.setOnClickListener {
            isGraphic = !isGraphic
            setRecordingButton()
        }

        binding.buttonExercise.setOnClickListener {
            if (exerciseGlobal.isStart) {
                exerciseGlobal.isStart = false
                binding.buttonExercise.text = "운동시작"

//                    TTSUtil.getInstance(instance)?.speakTextClear("운동을 종료합니다")
                Toast.makeText(this@PoseDetectorActivity, "운동 종료", Toast.LENGTH_LONG).show()
            } else {
                exerciseGlobal.isStart = true
                binding.buttonExercise.text = "운동종료"
                ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.START)
//                    TTSUtil.getInstance(instance)?.speakText("운동을 시작합니다")
                Toast.makeText(this@PoseDetectorActivity, "운동 시작", Toast.LENGTH_LONG).show()
            }

        }

//        var count = 0
//            val putDataMapRequest = PutDataMapRequest.create("/wear").apply {
//                count++
//                dataMap.putString("fromPhone", "Hello from phone $count")
//            }
//            val request = putDataMapRequest.asPutDataRequest().apply {
//                setUrgent()
//            }
//
//            Wearable.getDataClient(this).putDataItem(request)

        binding.animationView.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
//                ExerciseSoundUtil.getInstance(this@PoseDetectorActivity)!!
//                    .playSound(EXERCISE.ENDING_SOUND)
                binding.animationView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.animationView.visibility = View.GONE
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
                binding.animationView.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {
                binding.animationView.visibility = View.GONE
            }
        })

        binding.animationViewRest.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                binding.animationViewRest.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.animationViewRest.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
                binding.animationViewRest.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {
                binding.animationViewRest.visibility = View.GONE
            }
        })

    }

    private fun initEvent() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun initGlobalData(){
        ExerciseGlobal.nowSet = EXERCISE_INFORMATION.SET_FIRST_INT
        exerciseGlobal.isStart = false
        ExerciseGlobal.restBefore10Flag = true
        ExerciseGlobal.restBefore3Flag = true
    }

    /** pose detector */
    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        ExerciseSoundUtil.getInstance(instance)
//        TTSUtil.getInstance(instance)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val poseLogic = PoseLogicAndroid()
        var poseEngine = com.hnh.poseengine.logic.PoseLogic(osal, exerciseGlobal, viewModel)
        val testanaliser = PoseImageAnalyzer(poseLogic, poseEngine, exerciseGlobal,exerciseID, graphicOverlay)
        cameraProviderFuture.addListener(Runnable {
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(binding.viewFinder.width, binding.viewFinder.height))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), testanaliser)
            val cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val cameraSelector = cameraState
            isCameraFront = (cameraState != CameraSelector.DEFAULT_BACK_CAMERA)

            //영상 저장
            val qualitySelector = QualitySelector.of(QUALITY_LOWEST)

            val recorder = Recorder.Builder()
                .setQualitySelector(qualitySelector)
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            try {
                cameraProvider.unbindAll()

                if (hasRecordingCamera()) {
                    cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        videoCapture,
                        imageAnalysis,
                        preview
                    )
                } else {
                    cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                }

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hasRecordingCamera(): Boolean {
        //카메라가 Level3이고, 카메라가 뒷 카메라여야 함.
        return (isLevel3 && (cameraState == CameraSelector.DEFAULT_BACK_CAMERA))
    }

    /** Activity Lifecycle */
    override fun onStart() {
        super.onStart()
        initEvent()

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        exerciseGlobal = com.hnh.poseengine.logic.ExerciseGlobal(osal)

        PreferenceUtil.preferenceInstance(instance).isRecord = binding.checkboxRecording.isChecked //체크여부 초기화
    }

    /** Camera Recording*/
    private fun onRecord(boolean: Boolean) = when (boolean) {
        true -> startRecording()
        false -> stopRecording()
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        // create MediaStoreOutputOptions for our recorder: resulting our recording!
        if (activeRecording != null) {
            return
        }
        Toast.makeText(this, "녹화 시작", Toast.LENGTH_SHORT).show()

        val name = "FitGAI_${exerciseID}_" +
                SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA)
                    .format(System.currentTimeMillis()) + ".mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
            .setContentValues(contentValues)
            .build()

        // configure Recorder and Start recording to the mediaStoreOutput.
        activeRecording =
            videoCapture.output.prepareRecording(this, mediaStoreOutput)
                .apply { } //withAudioEnabled()
                .start()
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        if (activeRecording == null) {
            return
        }

        Toast.makeText(this, "녹화 시작", Toast.LENGTH_SHORT).show()
        val recording = activeRecording
        if (recording != null) {
            recording.stop()
            activeRecording = null
        }
//        videoCapture?.stopRecording()
    }

    /** wear watch message */
    override fun onResume() {
        super.onResume()
        try {
            Wearable.getDataClient(this).addListener(this)
            Wearable.getMessageClient(this).addListener(this)
            Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            Wearable.getDataClient(this).removeListener(this)
            Wearable.getMessageClient(this).removeListener(this)
            Wearable.getCapabilityClient(this).removeListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {

        Log.d("testtest", "data received : ${dataEvents.toString()}")
        dataEvents.forEach { event ->
            var path = event.dataItem.uri.path
            when (path) {
                VOICE_PATH -> {
                    Toast.makeText(this, "start voice from watch", Toast.LENGTH_LONG).show()
                    startVoice()
                }
                START_PATH -> {
                    if (!exerciseGlobal.isStart) {
                        exerciseGlobal.isStart = true
                        ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.START)
//                        TTSUtil.getInstance(instance)?.speakText("운동을 시작합니다")
                    } else {
                        ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.ALREADY)
//                        TTSUtil.getInstance(instance)?.speakText("이미 운동중입니다")
                    }
                }
                END_PATH -> {
                    Toast.makeText(this, "운동 종료", Toast.LENGTH_LONG).show()
                    if (exerciseGlobal.isStart) {
                        exerciseGlobal.isStart = false
                        ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.END)
//                        TTSUtil.getInstance(instance)?.speakTextClear("운동을 종료합니다")
                    } else {
                        ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.NO)
//                        TTSUtil.getInstance(instance)?.speakText("운동상태가 아닙니다")
                    }
                }
            }

        }
    }

    private fun startVoice() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(recognitionListener)
        speechRecognizer.startListening(sttIntent)

    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("testtest", "message received")
    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        Log.d("testtest", "onCapabilityChanged: $capabilityInfo")
    }

    private fun getNodes() {
        Thread {
            val nodeListTask = Wearable.getNodeClient(applicationContext).connectedNodes

            try {
                val nodes = Tasks.await(nodeListTask)
                runOnUiThread {
                    nodes.forEach {
                        Toast.makeText(this, it.displayName, Toast.LENGTH_LONG).show()

                        Wearable.getMessageClient(this)
                            .sendMessage(it.id, "/msg", "Data".toByteArray())
                    }
                }
                isWearable = true
            } catch (e: ExecutionException) {
                Log.w("WEAR", e.message ?: "ExecutionException")
                val nodes = null
                isWearable = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
                val nodes = null
                isWearable = false
            }
        }.start()
    }

    /** STT speech to text*/
    private fun setListener() {
        recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
//                TTSUtil.getInstance(instance)?.speakText("음성인식을 시작합니다")
                Toast.makeText(applicationContext, "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                val message: String = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                    SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "권한 없음"
                    SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 타임아웃"
                    SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER가 바쁨"
                    SpeechRecognizer.ERROR_SERVER -> "서버 이상"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                    else -> "알 수 없는 오류"
                }
                Toast.makeText(applicationContext, "에러 발생 $message", Toast.LENGTH_SHORT).show()
                ExerciseSoundUtil.getInstance(instance)!!.playSound(EXERCISE.REPEAT)
//                TTSUtil.getInstance(instance)?.speakText("다시 해주세요")
            }

            override fun onResults(results: Bundle?) {
                var matches: ArrayList<String> =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
                val startWord = "시작"
                val startWord2 = "시자"
                val endWord = "종료"
                val endWord2 = "종뇨"

                for (i in 0 until matches.size) {
                    Log.d("testtest", "말 : " + matches[i])
                    when (matches[i]) {
                        startWord, startWord2 -> {
                            if (!exerciseGlobal.isStart) {
                                Toast.makeText(
                                    this@PoseDetectorActivity,
                                    "운동 시작",
                                    Toast.LENGTH_SHORT
                                ).show()
                                exerciseGlobal.isStart = true
                                TTSUtil.getInstance(instance)?.speakText("운동을 시작합니다")
                            } else {
                                TTSUtil.getInstance(instance)?.speakText("이미 운동중입니다")
                            }
                        }
                        endWord, endWord2 -> {
                            if (exerciseGlobal.isStart) {
                                Toast.makeText(
                                    this@PoseDetectorActivity,
                                    "운동 종료",
                                    Toast.LENGTH_SHORT
                                ).show()
                                exerciseGlobal.isStart = false
                                TTSUtil.getInstance(instance)?.speakText("운동을 종료합니다")
                            } else {
                                TTSUtil.getInstance(instance)?.speakText("운동상태가 아닙니다")
                            }
                        }
                        else -> {
                            TTSUtil.getInstance(instance)?.speakText("없는 명령어입니다")
                        }
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
    }

    /** permission check */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun makeNoVideoDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage("꿀팁이 없습니다.")
            .setPositiveButton(resources.getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        const val START_ACTIVITY_PATH = "/start-activity"
        const val VOICE_PATH = "/voice"
        const val VOICE_KEY = "voice"
        const val COUNT_PATH = "/count"
        const val COUNT_KEY = "count"
        const val START_PATH = "/start"
        const val END_PATH = "/end"



        private const val TAG = "PoseDetection"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

        //카메라 셀렉터, 카메라 전면, 후면 확인용 Flag (GraphicOverlay 매칭에 사용)
        var cameraState: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        var isCameraFront: Boolean = false

        //TODO : buttonSet(false), isGraphic(true) Default setting
        //buttonSet, isGraphic, setData, viewtext 삭제후 디폴트 값으로 적용 예정
        var buttonSet = false
        var isGraphic = true

        var setData = Pair("Squat", true)
        var viewtext = "Squat"
        lateinit var graphicOverlay: GraphicOverlay
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val REQUEST_CODE_MEDIA_PROJECTION = 100
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
        // PoseLogic등 디텍팅 파트에서 엑티비티 Context get
        lateinit var instance: AppCompatActivity
        fun getPoseContext(): Context {
            return instance.applicationContext
        }
    }
}
