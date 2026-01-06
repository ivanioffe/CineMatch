plugins {
    alias(libs.plugins.cinematch.android.library)
    alias(libs.plugins.roborazzi)
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
                "proguard-rules.pro",
            )
        }
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    testFixturesImplementation(platform(libs.androidx.compose.bom))
    testFixturesImplementation(libs.androidx.ui.test.junit4)
    testFixturesImplementation(libs.androidx.ui.test.manifest)
    testFixturesImplementation(libs.composable.preview.scanner)
    testFixturesImplementation(libs.junit)
    testFixturesImplementation(libs.junit.vintage.engine)
    testFixturesImplementation(libs.robolectric)
    testFixturesImplementation(libs.roborazzi.previewScanner)
    testFixturesImplementation(libs.roborazzi.rule)
}
