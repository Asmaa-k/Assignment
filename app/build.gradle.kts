plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.realm.gradle)
    alias(libs.plugins.nav.safe.args)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.asmaa.khb.filterapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.asmaa.khb.filterapp"
        minSdk = 26
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    //lifecycle dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //hilt dependency-injection
    implementation(libs.hilt.android)
    implementation(libs.core.ktx)
    kapt(libs.hilt.android.compiler)

    //gson dependency
    implementation(libs.gson)

    //realm dependency
    implementation(libs.realm)

    //navigation dependency
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //recycler-view dependency
    implementation(libs.blender.adapter)

    //glide dependency
    implementation(libs.glide)

    //flexbox dependency
    implementation(libs.flexbox.layout)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    //realm test library
    testImplementation(libs.realm)
    testImplementation(libs.realm.test)
    testImplementation(libs.realm.library.sync)
    //json test library
    testImplementation(libs.serialization.json.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}