plugins {
    `kotlin-dsl`
    alias(libs.plugins.ktlint)
}

group = "com.ioffeivan.cinematch.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradleApiPlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.cinematch.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeatureUi") {
            id = libs.plugins.cinematch.android.feature.ui.get().pluginId
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.cinematch.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = libs.plugins.cinematch.compose.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.cinematch.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.cinematch.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("room") {
            id = libs.plugins.cinematch.room.get().pluginId
            implementationClass = "RoomConventionPlugin"
        }
    }
}
