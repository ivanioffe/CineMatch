plugins {
    `kotlin-dsl`
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
        register("androidLibrary") {
            id = libs.plugins.cinematch.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.cinematch.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
