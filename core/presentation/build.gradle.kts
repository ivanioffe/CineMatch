plugins {
    alias(libs.plugins.cinematch.android.library)
}

android {
    namespace = "com.ioffeivan.core.presentation"

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
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testFixturesCompileOnly(platform(libs.test.junit5.bom))
    testFixturesCompileOnly(libs.test.junit5.api)
    testFixturesCompileOnly(libs.kotlinx.coroutines.test)
}
