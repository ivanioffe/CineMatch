import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.cinematch.android.library)
    alias(libs.plugins.cinematch.hilt)
    alias(libs.plugins.kotlin.serialization)
}

val backendBaseUrl =
    providers.fileContents(
        isolated.rootProject.projectDirectory.file("local.properties"),
    ).asText.map { text ->
        val properties = Properties()
        properties.load(StringReader(text))
        properties["BACKEND_BASE_URL"] as String
    }.orElse("")

android {
    namespace = "com.ioffeivan.core.network"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BACKEND_BASE_URL", "\"${backendBaseUrl.get()}\"")
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.datastoreAuth)

    implementation(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    api(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    androidTestImplementation(libs.androidx.test.runner)
}
