package com.hnh.presentation.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import java.util.*

/**
 * Created by hyerim on 2021/10/14...
 */
class TTSUtil private constructor(context: Context) {
    private val mContext: Context
    private lateinit var textToSpeech: TextToSpeech

    companion object {
        private const val TAG = "TTSUtil"
        private var singleton: TTSUtil? = null

        fun getInstance(_context: Context): TTSUtil? {
            if (singleton == null) {
                synchronized(TTSUtil::class.java) {
                    if (singleton == null) {
                        singleton = TTSUtil(_context)
                    }
                }
            }
            return singleton
        }
    }

    init {
        mContext = context.applicationContext
        textToSpeech = TextToSpeech(mContext, OnInitListener { i ->
            if (i == TextToSpeech.SUCCESS) {
                textToSpeech!!.language = Locale.KOREAN
                textToSpeech!!.setPitch(1.3f)
                textToSpeech!!.setSpeechRate(1.7f)
            }
        })
    }

    fun speakText(text: String?) {
        //QUEUE_FLUSH : 대기열 삭제 후 새로 추가
        //QUEUE_ADD : 대기열 뒤에 추가
        if (!textToSpeech?.isSpeaking) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_ADD, null, null)

            Log.d(TAG, "is Speaking false")
        } else {
            Log.d(TAG, "is Speaking true")
        }

    }

    fun speakTextClear(text: String?) {
        //QUEUE_FLUSH : 대기열 삭제 후 새로 추가
        //QUEUE_ADD : 대기열 뒤에 추가
        if (!textToSpeech?.isSpeaking) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

            Log.d(TAG, "is Speaking false")
        } else {
            Log.d(TAG, "is Speaking true")
        }

    }

    fun shutDown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}