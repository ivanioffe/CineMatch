plugins {
    alias(libs.plugins.cinematch.jvm.library)
}

dependencies {
    testImplementation(platform(libs.test.junit5.bom))
    testImplementation(libs.test.junit5.api)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)

    testRuntimeOnly(libs.test.junit5.engine)
    testRuntimeOnly(libs.test.junit.platform.launcher)
}
