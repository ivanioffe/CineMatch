plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ktlint)
}
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
    testImplementation(platform(libs.test.junit5.bom))
    testImplementation(libs.test.junit5.api)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testRuntimeOnly(libs.test.junit5.engine)
    testRuntimeOnly(libs.test.junit.platform.launcher)
}
