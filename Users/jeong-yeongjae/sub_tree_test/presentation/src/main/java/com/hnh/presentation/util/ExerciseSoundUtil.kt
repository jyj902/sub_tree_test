package com.hnh.presentation.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.SystemClock
import com.hnh.presentation.R
import com.hnh.presentation.util.EXERCISE.RATING_1
import com.hnh.presentation.util.EXERCISE.RATING_2
import com.hnh.presentation.util.EXERCISE.RATING_3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.annotation.meta.When
import kotlin.collections.HashMap

/**
 * Created by hyerim on 2021/11/05...
 */
class ExerciseSoundUtil private constructor(context: Context) {
    private val mContext: Context
    private val soundPool = SoundPool.Builder().setMaxStreams(1).build()
    private var soundsLoaded: Boolean = false
    private var soundMap: HashMap<String, Int> = HashMap()
    private var isPlay: Boolean = false
    private var startTime = SystemClock.elapsedRealtime()
    private var endTime = SystemClock.elapsedRealtime()

    companion object {
        private const val TAG = "ExerciseSoundUtil"
        const val MAX_COUNT = 25
        const val DELAY = 1000L
        const val DELAY_COUNT = 600L
        const val VOLUME = 1.0f // 0.0 to 1.0

        private var singleton: ExerciseSoundUtil? = null

        fun getInstance(_context: Context): ExerciseSoundUtil? {
            if (singleton == null) {
                synchronized(ExerciseSoundUtil::class.java) {
                    if (singleton == null) {
                        singleton = ExerciseSoundUtil(_context)
                    }
                }
            }
            return singleton
        }
    }

    init {
        mContext = context.applicationContext
        //운동 개수 1~25개 사운드
        for (i in 1..MAX_COUNT) {
            val raw = mContext.resources.getIdentifier(
                "exercise_count_$i",
                "raw",
                mContext.packageName
            )
            soundMap["count_$i"] = soundPool.load(mContext, raw, 1)
        }

        //운동 효과음
        soundMap[EXERCISE.ENDING_SOUND] = soundPool.load(mContext, R.raw.exercise_ending, 1)//효과음

        //운동 시작, 종료, 이미 운동중, 운동상태가 아님
        soundMap[EXERCISE.START] = soundPool.load(mContext, R.raw.exercise_start, 1)
        soundMap[EXERCISE.END] = soundPool.load(mContext, R.raw.exercise_end, 1)
        soundMap[EXERCISE.ALREADY] = soundPool.load(mContext, R.raw.exercise_already, 1)
        soundMap[EXERCISE.NO] = soundPool.load(mContext, R.raw.exercise_no, 1)

        //운동별 종료
        soundMap[EXERCISE.SQUAT_3SET_END] =
            soundPool.load(mContext, R.raw.exercise_squat_3set_end, 1)  //스쿼트 3세트종료 수고했어요
        soundMap[EXERCISE.LUNGE_3SET_END] =
            soundPool.load(mContext, R.raw.exercise_lunge_3set_end, 1)  //런지 3세트종료 수고했어요
        soundMap[EXERCISE.LAT_PULL_DOWN_3SET_END] =
            soundPool.load(mContext, R.raw.exercise_lat_pull_down_3set_end, 1)  //랫풀다운 3세트종료 수고했어요
        soundMap[EXERCISE.SIDE_LATERAL_RAISE_3SET_END] =
            soundPool.load(
                mContext,
                R.raw.exercise_side_lateral_raise_3set_end,
                1
            )  //사이드레터럴레이즈 3세트종료 수고했어요
        soundMap[EXERCISE.SHOULDER_PRESS_3SET_END] =
            soundPool.load(
                mContext,
                R.raw.exercise_shoulder_press_3set_end,
                1
            )   //숄더프레스 3세트종료 수고했어요

        //휴식, 운동 진행세트
        soundMap[EXERCISE.REST] = soundPool.load(mContext, R.raw.exercise_rest, 1)  //휴식하세요
        soundMap[EXERCISE.SET_FIRST] =
            soundPool.load(mContext, R.raw.exercise_set_first, 1)  //1세트 시작합니다
        soundMap[EXERCISE.SET_SECOND] =
            soundPool.load(mContext, R.raw.exercise_set_second, 1) //2세트 시작합니다
        soundMap[EXERCISE.SET_THIRD] =
            soundPool.load(mContext, R.raw.exercise_set_third, 1)  //3세트 시작합니다
        soundMap[EXERCISE.REST_BEFORE_10] =
            soundPool.load(mContext, R.raw.exercise_rest_before_10, 1)  //휴식시간 종료 10초전 준비해주세요
        soundMap[EXERCISE.REST_BEFORE_3] =
            soundPool.load(mContext, R.raw.exercise_rest_before_3, 1)  //휴식시간 종료

        //녹화 시작, 종료
        soundMap[EXERCISE.RECORDING_START] = soundPool.load(mContext, R.raw.exercise_recording, 1)
        soundMap[EXERCISE.RECORDING_END] =
            soundPool.load(mContext, R.raw.exercise_recording_stop, 1)

        //todo 4단계로 변경 Perfect, Good, SoSo, Bad
        //운동 평가
        soundMap[EXERCISE.RATING.PERFECT_1] = soundPool.load(mContext, R.raw.exercise_perfect_1, 1)
        soundMap[EXERCISE.RATING.PERFECT_2] = soundPool.load(mContext, R.raw.exercise_perfect_2, 1)
        soundMap[EXERCISE.RATING.GOOD_1] = soundPool.load(mContext, R.raw.exercise_good_1, 1)
        soundMap[EXERCISE.RATING.GOOD_2] = soundPool.load(mContext, R.raw.exercise_good_2, 1)
        soundMap[EXERCISE.RATING.SOSO_1] = soundPool.load(mContext, R.raw.exercise_soso_1, 1)
        soundMap[EXERCISE.RATING.SOSO_2] = soundPool.load(mContext, R.raw.exercise_soso_2, 1)
        soundMap[EXERCISE.RATING.SOSO_3] = soundPool.load(mContext, R.raw.exercise_soso_3, 1)
        soundMap[EXERCISE.END_PERFECT] =
            soundPool.load(mContext, R.raw.exercise_end_perfect, 1) //성공적인 운동이었어요

        //공통적인 조언
        soundMap[EXERCISE.REPEAT] = soundPool.load(mContext, R.raw.exercise_repeat, 1) //다시하세요
        soundMap[EXERCISE.MOVE] = soundPool.load(mContext, R.raw.exercise_move, 1) //움직이세요
        soundMap[EXERCISE.FRONT] = soundPool.load(mContext, R.raw.exercise_front, 1) //정면을 바라봐주세요
        soundMap[EXERCISE.FAST] = soundPool.load(mContext, R.raw.exercise_fast, 1) //속도가 너무 빠릅니다
        soundMap[EXERCISE.SHRINK] = soundPool.load(mContext, R.raw.exercise_shrink, 1) //끝까지 수축해주세요
        soundMap[EXERCISE.RELAXATION] =
            soundPool.load(mContext, R.raw.exercise_relaxation, 1) //끝까지 이완해주세요

        //운동 상세 조언
        soundMap[EXERCISE.KNEES_TOES] =
            soundPool.load(mContext, R.raw.exercise_knees_toes, 1) //무릎을 발끝으로 향해
        soundMap[EXERCISE.ARMS_STRAIGHT] =
            soundPool.load(mContext, R.raw.exercise_arms_straight, 1) //팔을 일자로 피세요
        soundMap[EXERCISE.SHOULDER_OVER] =
            soundPool.load(mContext, R.raw.exercise_shoulder_over, 1) //어깨위로 손을 올리지 마세요
        soundMap[EXERCISE.SIDE_HANDS] =
            soundPool.load(mContext, R.raw.exercise_side_hands, 1) //팔꿈치를 옆으로 들어주세요
        soundMap[EXERCISE.SPREAD_SHOULDER] =
            soundPool.load(mContext, R.raw.exercise_spread_shoulder, 1) //어깨넓이로 벌려주세요
        soundMap[EXERCISE.HIP] =
            soundPool.load(mContext, R.raw.exercise_hip, 1) //엉덩이를 뒤로 빼주세요
        soundMap[EXERCISE.WAIST] =
            soundPool.load(mContext, R.raw.exercise_waist, 1) //허리를 펴주세요
        soundMap[EXERCISE.HEAD_DUMBBELL] =
            soundPool.load(mContext, R.raw.exercise_head_dumbbell, 1) //팔꿈치를 90도로 유지해주세요
        soundMap[EXERCISE.UP_TO_HEAD_DUMBBELL] =
            soundPool.load(mContext, R.raw.exercise_up_to_head_dumbbell, 1) //덤벨을 머리 위로 들어주세요
        soundMap[EXERCISE.KNEE_DEGREE_90] =
            soundPool.load(mContext, R.raw.exercise_knee_degree_90, 1) //무릎을 90도까지 앉으세요
        soundMap[EXERCISE.KNEE_FOOT_ANGLE] =
            soundPool.load(mContext, R.raw.exercise_knee_foot_angle, 1) //앞쪽 무릎을 정면으로 뻗어주세요
        soundMap[EXERCISE.DOWN_WRIST] =
            soundPool.load(mContext, R.raw.exercise_down_wrist, 1) //덤벨을 더 내려주세요
        soundMap[EXERCISE.UP_WRIST] =
            soundPool.load(mContext, R.raw.exercise_up_wrist, 1) //덤벨을 끝까지 밀어주세요
        soundMap[EXERCISE.HIP_VERTICAL] =
            soundPool.load(mContext, R.raw.exercise_hip_vertical, 1) //수직으로 앉아주세요
        soundMap[EXERCISE.FOOT_PELVIS_DISTANCE] =
            soundPool.load(mContext, R.raw.exercise_foot_pelvis_distance, 1) //다리를 골반넓이로 벌려주세요
        soundMap[EXERCISE.UP_ELBOW] =
            soundPool.load(mContext, R.raw.exercise_up_elbow, 1) //팔꿈치를 올려주세요
        soundMap[EXERCISE.ELBOW_STRAIGHT] =
            soundPool.load(mContext, R.raw.exercise_elbow_straight, 1) //팔꿈치를 끝까지 펴주세요
        soundMap[EXERCISE.ELBOW_VERTICAL] =
            soundPool.load(mContext, R.raw.exercise_elbow_vertical, 1) //팔꿈치를 일직선으로 내려주세요
        soundMap[EXERCISE.LONG_BAR] =
            soundPool.load(mContext, R.raw.exercise_long_bar, 1) //바를 더 넓게 잡으세요
        soundMap[EXERCISE.DOWN_BAR] =
            soundPool.load(mContext, R.raw.exercise_down_bar, 1) //바를 더 내려주세요
        soundMap[EXERCISE.FIX_ELBOW] =
            soundPool.load(mContext, R.raw.exercise_fix_elbow, 1) //팔꿈치를 고정해주세요
        soundMap[EXERCISE.FIX_HIP] =
            soundPool.load(mContext, R.raw.exercise_fix_hip, 1) //허리를 고정해주세요
        soundMap[EXERCISE.FOOT_STRAIGHT] =
            soundPool.load(mContext, R.raw.exercise_foot_straight, 1) //발을 정면으로 뻗어주세요
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundsLoaded = true
        }
    }

    fun playSound(key: String) {
        if (soundsLoaded && !isPlay) {
            CoroutineScope(IO).launch {
                isPlay = true
                soundPool.play(soundMap[key]!!, VOLUME, VOLUME, 1, 0, 1f)
                delay(DELAY)
                isPlay = false
            }
        }
    }

    fun playOnlyCountSound(count: Int) {
        if (count < 0) return

        //25개 까지만 개수 출력
        if (soundsLoaded && !isPlay) {
            CoroutineScope(IO).launch {
                isPlay = true
                if (count in 1..MAX_COUNT) {
                    soundPool.play(soundMap["count_$count"]!!, VOLUME, VOLUME, 1, 0, 1f)
                    delay(DELAY_COUNT)
                }
                isPlay = false
            }
        }
    }

    fun playCountSound(count: Int, rating: String) {
        if (count < 0) return

        //25개 까지만 개수 출력
        if (soundsLoaded && !isPlay) {
            CoroutineScope(IO).launch {
                isPlay = true
                if (count in 1..MAX_COUNT) {
                    soundPool.play(soundMap["count_$count"]!!, VOLUME, VOLUME, 1, 0, 1f)
                    delay(DELAY_COUNT)
                }
                soundPool.play(soundMap[rating]!!, VOLUME, VOLUME, 1, 0, 1f)
                delay(DELAY)
                isPlay = false
            }
        }
    }

    fun playBackgroundSound(key: String) {
        if (soundsLoaded && !isPlay) {
            CoroutineScope(IO).launch {
                isPlay = true
                soundPool.play(soundMap[key]!!, VOLUME, VOLUME, 0, 0, 1.1f)
                delay(DELAY)
                isPlay = false
            }
        }
    }

    fun playRandomRating(count: Int, rating: String) {
        if (count < 0) return

        var item = ""
        var index = count%2 + 1
        when (rating) {
            RATING_1 -> item = "perfect_$index"
            RATING_2 -> item = "good_$index"
            RATING_3 -> {
                var index = (1..3).random()
                item = "soso_$index"
            }
        }

        //25개 까지만 개수 출력
        if ((item != "") && soundsLoaded && !isPlay) {
            CoroutineScope(IO).launch {
                isPlay = true
                if (count in 1..MAX_COUNT) {
                    soundPool.play(soundMap["count_$count"]!!, VOLUME, VOLUME, 1, 0, 1.2f)
                    delay(DELAY_COUNT)
                }
                soundPool.play(soundMap[item]!!, VOLUME, VOLUME, 1, 0, 1.2f)
                delay(DELAY)
                isPlay = false
            }

        }
    }

}