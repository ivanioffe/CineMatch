plugins {
    alias(libs.plugins.cinematch.android.library)
    alias(libs.plugins.cinematch.compose)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.ioffeivan.core.ui"

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
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.truth)

    debugImplementation(libs.androidx.ui.test.manifest)
}
