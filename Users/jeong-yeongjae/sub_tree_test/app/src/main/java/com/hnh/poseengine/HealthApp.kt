package com.hnh.poseengine

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by hyerim on 9/16/21...
 */
@HiltAndroidApp
class HealthApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        //서버알림 수신을 위한 임시 주제 구독
        Firebase.messaging.subscribeToTopic("a")

        DynamicColors.applyToActivitiesIfAvailable(this);
    }

}
