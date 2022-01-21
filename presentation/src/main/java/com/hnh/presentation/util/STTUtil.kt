package com.hnh.presentation.util

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

/**
 * Created by hyerim on 2021/10/15...
 */
class STTUtil(private val context: Context) : RecognitionListener {

    private var recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private var speech: SpeechRecognizer? = null
    private var audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var isStop = false

    init {
        recognizerIntent.run {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
            }
        }

        initializeRecognizer()
    }

    private fun initializeRecognizer() {
        speech = SpeechRecognizer.createSpeechRecognizer(context)
        speech?.setRecognitionListener(this)
    }

    fun destroyRecognizer() {
        speech?.destroy()
    }

    fun startRecognition() {
        speech?.startListening(recognizerIntent)
        isStop = false
    }

    fun stopRecognition() {
        speech?.stopListening()
        isStop = true
    }

    fun cancelRecognition() {
        speech?.cancel()
    }

    @Suppress("DEPRECATION")
    private fun muteRecognition(mute: Boolean) {
        audioManager.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val flag = if (mute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
                adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, flag, 0)
                adjustStreamVolume(AudioManager.STREAM_ALARM, flag, 0)
                adjustStreamVolume(AudioManager.STREAM_MUSIC, flag, 0)
                adjustStreamVolume(AudioManager.STREAM_RING, flag, 0)
                adjustStreamVolume(AudioManager.STREAM_SYSTEM, flag, 0)
            } else {
                setStreamMute(AudioManager.STREAM_NOTIFICATION, mute)
                setStreamMute(AudioManager.STREAM_ALARM, mute)
                setStreamMute(AudioManager.STREAM_MUSIC, mute)
                setStreamMute(AudioManager.STREAM_RING, mute)
                setStreamMute(AudioManager.STREAM_SYSTEM, mute)
            }
        }
    }

    override fun onBeginningOfSpeech() {
    }

    override fun onReadyForSpeech(params: Bundle) {
    }

    override fun onBufferReceived(buffer: ByteArray) {
    }

    override fun onRmsChanged(rmsdB: Float) {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(errorCode: Int) {
        muteRecognition(true)
        if (!isStop) {
//            callback?.onError(errorCode)
            when (errorCode) {
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> cancelRecognition()
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                    destroyRecognizer()
                    initializeRecognizer()
                }
            }
            startRecognition()
        }
    }

    override fun onEvent(eventType: Int, params: Bundle) {
    }

    override fun onPartialResults(partialResults: Bundle) {
    }

    override fun onResults(results: Bundle) {
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
//        callback?.onResults(matches, scores)
        startRecognition()
    }
}