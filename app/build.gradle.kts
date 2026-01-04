plugins {
    alias(libs.plugins.cinematch.android.application)
    alias(libs.plugins.cinematch.compose)
    alias(libs.plugins.cinematch.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.ioffeivan.cinematch"

    defaultConfig {
        applicationId = "com.ioffeivan.cinematch"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(projects.feature.auth)
    implementation(projects.feature.movieDetails)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.searchMovies)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    androidTestImplementation(libs.androidx.test.runner)
}
