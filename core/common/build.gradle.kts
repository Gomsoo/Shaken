plugins {
    alias(libs.plugins.shaken.jvm.library)
    alias(libs.plugins.shaken.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
