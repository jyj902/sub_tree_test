package com.hnh.presentation.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.hnh.presentation.util.EXERCISE_INFORMATION.SETTING_MODE
import com.hnh.presentation.util.EXERCISE_INFORMATION.SETTING_REPS
import com.hnh.presentation.util.EXERCISE_INFORMATION.SETTING_REST_TIME
import com.hnh.presentation.util.EXERCISE_INFORMATION.SETTING_SET

/**
 * Created by hyerim on 2021/10/25...
 */
class PreferenceUtil private constructor(private val mContext: Context){
    private val sharedPreferences: SharedPreferences

    companion object {
        private val SP_NAME = "healthapp"
        private var preferenceUtils: PreferenceUtil? = null

        fun preferenceInstance(context: Context): PreferenceUtil {
            return PreferenceUtil(context)
        }

        private fun removeInstance() {
            preferenceUtils = null
        }
    }

    init {
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, MODE_PRIVATE)
    }

    fun clearAllPrefs() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        removeInstance()
    }

    var isLogin: Boolean
        get() = sharedPreferences.getBoolean("is_login", false)
        set(islogin) = sharedPreferences.edit().putBoolean("is_login", islogin).apply()

    var isToken: Boolean
        get() = sharedPreferences.getBoolean("is_token", false)
        set(islogin) = sharedPreferences.edit().putBoolean("is_token", islogin).apply()

    var authToken: String?
        get() = sharedPreferences.getString("auth_token", "")
        set(deviceToken) = sharedPreferences.edit().putString("auth_token", deviceToken).apply()

    var socialType: String?
        get() = sharedPreferences.getString("social_type", "")
        set(socialId) = sharedPreferences.edit().putString("social_type", socialId).apply()

    var userUid: Long?
        get() = sharedPreferences.getLong("user_uid", 0)
        set(userUid) = sharedPreferences.edit().putLong("user_uid", userUid!!).apply()

    var userName: String?
        get() = sharedPreferences.getString("user_name", "")
        set(userName) = sharedPreferences.edit().putString("user_name", userName).apply()

    var isRecord: Boolean
        get() = sharedPreferences.getBoolean("is_record", false)
        set(isRecord) = sharedPreferences.edit().putBoolean("is_record", isRecord).apply()

    var squatModeFree: Int
        get() = sharedPreferences.getInt("squat_mode", 1) //mode = 1 이어야함
        set(isFreeMode) = sharedPreferences.edit().putInt("squat_mode", isFreeMode).apply()

    var squatSet: Int
        get() = sharedPreferences.getInt("squat_set", 3)
        set(squatSet) = sharedPreferences.edit().putInt("squat_set", squatSet).apply()

    var squatRestTime: Int
        get() = sharedPreferences.getInt("squat_rest_time", 40000)
        set(squatRestTime) = sharedPreferences.edit().putInt("squat_rest_time", squatRestTime).apply()

    var squatExerciseReps: Int
        get() = sharedPreferences.getInt("squat_exercise_reps", 10)
        set(squatExerciseCount) = sharedPreferences.edit().putInt("squat_exercise_reps", squatExerciseCount).apply()

    var lungeModeFree: Int
        get() = sharedPreferences.getInt("lunge_mode", 1) //mode = 1 이어야함
        set(isFreeMode) = sharedPreferences.edit().putInt("lunge_mode", isFreeMode).apply()

    var lungeSet: Int
        get() = sharedPreferences.getInt("lunge_set", 3)
        set(lungeSet) = sharedPreferences.edit().putInt("lunge_set", lungeSet).apply()

    var lungeRestTime: Int
        get() = sharedPreferences.getInt("lunge_rest_time", 40000)
        set(lungeRestTime) = sharedPreferences.edit().putInt("lunge_rest_time", lungeRestTime).apply()

    var lungeExerciseReps: Int
        get() = sharedPreferences.getInt("lunge_exercise_reps", 10)
        set(lungeExerciseCount) = sharedPreferences.edit().putInt("lunge_exercise_reps", lungeExerciseCount).apply()

    var defaultModeFree: Int
        get() = sharedPreferences.getInt("default_mode", 1) //mode = 1 이어야함
        set(defaultModeFree) = sharedPreferences.edit().putInt("default_mode", defaultModeFree).apply()

    var defaultSet: Int
        get() = sharedPreferences.getInt("default_set", 3)
        set(defaultSet) = sharedPreferences.edit().putInt("default_set", defaultSet).apply()

    var defaultRestTime: Int
        get() = sharedPreferences.getInt("default_rest_time", 40000)
        set(defaultRestTime) = sharedPreferences.edit().putInt("default_rest_time", defaultRestTime).apply()

    var defaultExerciseReps: Int
        get() = sharedPreferences.getInt("default_exercise_reps", 10)
        set(defaultExerciseReps) = sharedPreferences.edit().putInt("default_exercise_reps", defaultExerciseReps).apply()

    fun getExerciseSetting(exerciseID: String, data: Int): Int{
        when(exerciseID){
            EXERCISE_LIST.SQUAT ->
                when(data){
                    SETTING_MODE -> return squatModeFree
                    SETTING_SET -> return squatSet
                    SETTING_REPS -> return squatExerciseReps
                    SETTING_REST_TIME -> return squatRestTime
                }
            EXERCISE_LIST.LUNGE ->
                when(data){
                    SETTING_MODE -> return lungeModeFree
                    SETTING_SET -> return lungeSet
                    SETTING_REPS -> return lungeExerciseReps
                    SETTING_REST_TIME -> return lungeRestTime
                }
            else ->
                when(data){
                SETTING_MODE -> return defaultModeFree
                SETTING_SET -> return defaultSet
                SETTING_REPS -> return defaultExerciseReps
                SETTING_REST_TIME -> return defaultRestTime
            }
        }
        return 0
    }

    fun setExerciseSetting(exerciseID: String, data: Int, changeData : Int){
        when(exerciseID){
            EXERCISE_LIST.SQUAT ->
                when(data){
                    SETTING_MODE -> squatModeFree = changeData
                    SETTING_SET -> squatSet = changeData
                    SETTING_REPS -> squatExerciseReps = changeData
                    SETTING_REST_TIME -> squatRestTime = changeData
                }
            EXERCISE_LIST.LUNGE ->
                when(data){
                    SETTING_MODE -> lungeModeFree = changeData
                    SETTING_SET -> lungeSet = changeData
                    SETTING_REPS -> lungeExerciseReps = changeData
                    SETTING_REST_TIME -> lungeRestTime = changeData
                }
            else ->
                when(data){
                    SETTING_MODE -> defaultModeFree = changeData
                    SETTING_SET -> defaultSet = changeData
                    SETTING_REPS -> defaultExerciseReps = changeData
                    SETTING_REST_TIME -> defaultRestTime = changeData
                }
        }
    }
}