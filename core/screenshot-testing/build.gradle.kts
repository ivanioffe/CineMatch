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
    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
}

dependencies {
    testFixturesImplementation(platform(libs.androidx.compose.bom))
    testFixturesImplementation(libs.androidx.ui.test.junit4)
    testFixturesImplementation(libs.androidx.ui.test.manifest)
    testFixturesImplementation(libs.composable.preview.scanner)
    testFixturesImplementation(libs.junit)
    testFixturesImplementation(libs.robolectric)
    testFixturesImplementation(libs.roborazzi.previewScanner)
}
