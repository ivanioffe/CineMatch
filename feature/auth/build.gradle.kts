plugins {
    alias(libs.plugins.cinematch.android.feature.ui)
    alias(libs.plugins.cinematch.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.ioffeivan.feature.auth"

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
    implementation(projects.core.common)
    implementation(projects.core.datastoreAuth)
    implementation(projects.core.network)
    implementation(projects.core.presentation)

    implementation(libs.lottie.compose)
    implementation(libs.otp.compose)

    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit.vintage.engine)
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.rule)
    testImplementation(platform(libs.test.junit5.bom))
    testImplementation(libs.test.junit5.api)
    testImplementation(libs.test.junit5.params)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(testFixtures(projects.core.presentation))

    testRuntimeOnly(libs.test.junit.platform.launcher)
    testRuntimeOnly(libs.test.junit5.engine)

    androidTestImplementation(libs.androidx.test.runner)
}
