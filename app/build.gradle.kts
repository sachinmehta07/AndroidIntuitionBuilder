plugins {


    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-parcelize")
    id("kotlin-kapt")

}

android {
    namespace = "com.app.kotlinbasicslearn"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.kotlinbasicslearn"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.ssp.android)
    implementation(libs.sdp.android)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //for room database
    // add below dependency for using room.
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)


    //Glide image loading
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //workManager
    implementation(libs.androidx.work.runtime.ktx)

}