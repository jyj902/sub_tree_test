plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.androidHilt)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kotlinKapt)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = AndroidClient.testRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "IS_DEBUG", "true")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(Libraries.kotlinxCoroutine)
    implementation(Libraries.kotlinxCoroutineAndroid)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConvert)
    implementation(Libraries.retrofitScalarsConverter)
    implementation(Libraries.retrofitRxJava)
    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogginIterator)
    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)

    implementation(project(Module.domain))

//    api project.androidxAppCompat
}