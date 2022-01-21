package com.hnh.healthapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hnh.healthapp.Greeting
import android.widget.TextView
import com.hnh.healthapp.logic.ExerciseGlobal
import com.hnh.healthapp.logic.PoseInfoScreen
import com.hnh.healthapp.logic.PoseLogic

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var osal = AndroidOSAL()
        var exerciseGlobal = ExerciseGlobal(osal)
        var infoScreen: PoseInfoScreen = InfoScreen()
        var poseLogic : PoseLogic = PoseLogic(osal, exerciseGlobal, infoScreen)
        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }
}
