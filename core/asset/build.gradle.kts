plugins {
    alias(libs.plugins.shaken.android.library)
    alias(libs.plugins.shaken.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.gomsoo.shaken.core.asset"
}

dependencies {
    api(projects.core.common)

    implementation(libs.kotlinx.serialization.json)
}
