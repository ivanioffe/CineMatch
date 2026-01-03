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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit.vintage.engine)
    testImplementation(libs.robolectric)
    testImplementation(libs.truth)
}
