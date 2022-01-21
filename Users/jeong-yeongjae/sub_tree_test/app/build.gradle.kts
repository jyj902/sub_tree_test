import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.androidHilt)
    id(BuildPlugins.googleServices)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kotlinKapt)

//    id(ScriptPlugins.variants)
//    id(ScriptPlugins.infrastructure)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        applicationId = AndroidClient.appId
        versionCode = AndroidClient.versionCode
        versionName = AndroidClient.versionName
        testInstrumentationRunner = AndroidClient.testRunner

        manifestPlaceholders["kakaoApiKey"] = "kakao_native_key_xml"
        buildConfigField("String", "KAKAO_API_KEY", gradleLocalProperties(rootDir).getProperty("kakao_native_key"))
        buildConfigField("String", "NAVER_CLIENT_ID", gradleLocalProperties(rootDir).getProperty("naver_client_id"))
        buildConfigField("String", "NAVER_CLIENT_KEY", gradleLocalProperties(rootDir).getProperty("naver_client_key"))
        buildConfigField("String", "GOOGLE_CLIENT_ID", gradleLocalProperties(rootDir).getProperty("google_client_id"))

    }
    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }

//        //TODO: Remove this when migrating the DI framework
//        getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "IS_DEBUG", "true")
        }

        release {
//            isDebuggable = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "IS_DEBUG", "false")
//            signingConfig signingConfigs.release
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
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.kotlinxCoroutine)
    implementation(Libraries.kotlinxCoroutineAndroid)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConvert)
    implementation(Libraries.retrofitMoshiConvert)
    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogginIterator)
    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)
    implementation(Libraries.kakaoLogin)
    implementation(Libraries.googleLogin)
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseFcmKotlin)
    implementation(Libraries.firebaseAnalyticsKotlin)

    implementation(project(Module.data))
    implementation(project(Module.domain))
    implementation(project(Module.presentation))
}