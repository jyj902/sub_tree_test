plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.androidHilt)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kotlinKapt)
    id(BuildPlugins.googleServices)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = AndroidClient.testRunner
        consumerProguardFiles("consumer-rules.pro")

        manifestPlaceholders["kakaoApiKey"] = "kakao_native_key_xml"
        buildConfigField(
            "String",
            "KAKAO_API_KEY",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
                .getProperty("kakao_native_key")
        )
        buildConfigField(
            "String",
            "NAVER_CLIENT_ID",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
                .getProperty("naver_client_id")
        )
        buildConfigField(
            "String",
            "NAVER_CLIENT_KEY",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
                .getProperty("naver_client_key")
        )
        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
                .getProperty("google_client_id")
        )
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("boolean", "IS_DEBUG", "true")
        }

        release {
            isMinifyEnabled = true
            buildConfigField("boolean", "IS_DEBUG", "false")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
    buildFeatures {
        dataBinding = true
    }

}

dependencies {
    coreLibraryDesugaring(Libraries.desugar)
    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.coreKtx)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.junitTestExt)
    androidTestImplementation(TestLibraries.espressoCore)

    implementation(Libraries.appCompat)
    implementation(Libraries.activity)
    implementation(Libraries.fragment)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.navigation)
    implementation(Libraries.navigationKtx)
    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.glide)
    implementation(Libraries.glideAnnotation)
    implementation(Libraries.exoplayer)
    implementation(Libraries.exoplayerDash)
    implementation(Libraries.exoplayerUi)
    implementation(Libraries.lottie)
    implementation(Libraries.mpAndroidChart)
    implementation(Libraries.annotation)
    implementation(Libraries.kotlinxCoroutine)
    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)
    implementation(Libraries.lifecycleLivedata)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.kakaoLogin)
    implementation(Libraries.googleLogin)

    implementation(Libraries.mlkitPoseDetection)
    implementation(Libraries.mlkitPoseDetectionAccurate)
    implementation(Libraries.camera2)
    implementation(Libraries.cameraLifecycle)
    implementation(Libraries.cameraVideo)
    implementation(Libraries.cameraView)
    implementation(Libraries.guava)
    implementation(Libraries.openCsv)

    //wear
    implementation(WearLibraries.wear)
    implementation(WearLibraries.wearable)

    implementation(project(Module.domain))
    implementation(project(Module.poseEngine))
}