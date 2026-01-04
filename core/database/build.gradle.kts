plugins {
    alias(libs.plugins.cinematch.android.library)
    alias(libs.plugins.cinematch.hilt)
    alias(libs.plugins.cinematch.room)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.ioffeivan.core.database"

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
    implementation(libs.androidx.paging.runtime)
    implementation(libs.room.paging)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.truth)
}
