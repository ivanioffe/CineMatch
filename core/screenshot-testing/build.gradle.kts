plugins {
    alias(libs.plugins.cinematch.android.library)
}

android {
    namespace = "com.ioffeivan.core.screenshot_testing"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
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
}
