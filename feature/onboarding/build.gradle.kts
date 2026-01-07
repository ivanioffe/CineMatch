plugins {
    alias(libs.plugins.cinematch.android.feature.ui)
    alias(libs.plugins.cinematch.screenshotTesting)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ioffeivan.feature.onboarding"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
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
    testImplementation(libs.truth)
}
