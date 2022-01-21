object Kotlin {
    const val standardLibrary = "1.6.10"
    const val coroutines = "1.6.0"
}

object AndroidSdk {
    const val min = 23
    const val compile = 31
    const val target = compile
}

object AndroidClient {
    const val appId = "com.hnh.healthapp"
    const val versionCode = 100012
    const val versionName = "1.0.12"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.0.2"
        const val gradleVersion = "7.0.2"
        const val hilt = "2.40"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.standardLibrary}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val googleServicePlugin = "com.google.gms:google-services:4.3.10"
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "android"
    const val kotlinKapt = "kapt"
    const val kotlinParcelize = "parcelize"
    const val androidHilt = "dagger.hilt.android.plugin"
    const val googleServices = "com.google.gms.google-services"
}

object ScriptPlugins {
    const val variants = "scripts.variants"
    const val infrastructure = "scripts.infrastructure"
    const val quality = "scripts.quality"
    const val compilation = "scripts.compilation"
}

object Module {
    const val presentation = ":presentation"
    const val data = ":data"
    const val domain = ":domain"
    const val poseEngine = ":PoseEngine"
}

object Libraries {
    private object Versions {
        const val hilt = BuildPlugins.Versions.hilt

        const val desugar = "1.1.5"
        const val ktx = "1.6.0"
        const val annotation = "1.2.0"
        const val guava = "30.1.1-android"

        const val mlkit = "17.0.1-beta7"
        const val camera = "1.1.0-alpha11"
        const val cameraView = "1.0.0-alpha31"
        const val cameraVideo = "1.1.0-alpha11"
        const val openCsv = "5.5.2"

        const val lifecycle = "2.4.0"

        const val retrofit = "2.9.0"
        const val okhttp = "4.9.0"
        const val firebaseFcm = "28.4.2"
        const val prettyLogger = "2.2.0"
        const val kakaoLogin = "2.8.2"
        const val naverLogin = "4.2.6"
        const val googleLogin = "19.2.0"

        const val appCompat = "1.4.0"
        const val constraintLayout = "2.1.0"
        const val cardView = "1.0.0"
        const val recyclerView = "1.2.1"
        const val activity = "1.4.0"
        const val fragment = "1.4.0"
        const val navigation = "2.3.5"
        const val material = "1.6.0-alpha01"
        const val paging = "3.1.0"
        const val exoplayer = "2.15.0"
        const val glide = "4.12.0"
        const val lottie = "4.2.2"
        const val mpAndroidChart = "3.1.0"

        //TODO : Base 제거 후 삭제
        const val rxJava = "3.0.0"
        //
    }

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"
    const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
    const val guava = "com.google.guava:guava:${Versions.guava}"

    //pose detection 관련
    const val mlkitPoseDetection = "com.google.mlkit:pose-detection:${Versions.mlkit}"
    const val mlkitPoseDetectionAccurate = "com.google.mlkit:pose-detection-accurate:${Versions.mlkit}"
    const val camera2 = "androidx.camera:camera-camera2:${Versions.camera}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraView}"
    const val cameraVideo = "androidx.camera:camera-video:${Versions.cameraVideo}"
    const val openCsv = "com.opencsv:opencsv:${Versions.openCsv}"

    //kotlin, kotlinx
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.standardLibrary}"
    const val kotlinxCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Kotlin.coroutines}"
    const val kotlinxCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Kotlin.coroutines}"

    //lifecycle
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //remote
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConvert = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitGsonConvert = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
    const val retrofitScalarsConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogginIterator = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    //firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseFcm}"
    const val firebaseFcmKotlin = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseAnalyticsKotlin = "com.google.firebase:firebase-analytics-ktx"

    //logger
    const val prettyLogger = "com.orhanobut:logger:${Versions.prettyLogger}"

    //login
    const val kakaoLogin = "com.kakao.sdk:v2-user:${Versions.kakaoLogin}"
    const val naverLogin = "com.naver.nid:naveridlogin-android-sdk:${Versions.naverLogin}"
    const val googleLogin = "com.google.android.gms:play-services-auth:${Versions.googleLogin}"

    //UI
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val navigation = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationFeature = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingCommon = "androidx.paging:paging-common:${Versions.paging}"
    const val pagingRxjava3 = "androidx.paging:paging-rxjava3:${Versions.paging}"
    const val exoplayer = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayer}"
    const val exoplayerDash = "com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayer}"
    const val exoplayerUi = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayer}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotation = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val mpAndroidChart = "com.github.PhilJay:MPAndroidChart:v${Versions.mpAndroidChart}"


    //TODO : Base 제거 후 삭제
    const val rxKotlin = "io.reactivex.rxjava3:rxkotlin:$${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:$${Versions.rxJava}"

    //필요할 경우 사용
//    /* Dependency - nugu */
//    def nugu_version = "1.6.2"
//    nugu = "com.skt.nugu.sdk:nugu-android-helper:$nugu_version"
//    nuguUx = "com.skt.nugu.sdk:nugu-ux-kit:$nugu_version"
//    nuguLogin = "com.skt.nugu.sdk:nugu-login-kit:$nugu_version"
}

object WearLibraries {
    private object Versions {
        const val wear = "1.0.0"
        const val wearable = "17.1.0"
    }

    const val wear = "androidx.wear:wear-remote-interactions:${Versions.wear}"
    const val wearable = "com.google.android.gms:play-services-wearable:${Versions.wearable}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13.1"
        const val robolectric = "4.4"
        const val espressoCore = "3.4.0"
        const val junitTestExt = "1.1.3"
        const val hiltTesting = BuildPlugins.Versions.hilt
    }

    const val junit4 = "junit.junit:${Versions.junit4}"
    const val junitTestExt = "androidx.test.ext:junit:${Versions.junitTestExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltTesting}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "2.7"
    }

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}