plugins {
    alias(libs.plugins.cinematch.android.library)
    alias(libs.plugins.cinematch.compose)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.ioffeivan.core.designsystem"

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
