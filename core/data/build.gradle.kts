plugins {
    alias(libs.plugins.shaken.android.library)
    alias(libs.plugins.shaken.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.gomsoo.shaken.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.network)
    api(projects.core.database)
    api(projects.core.asset)

    implementation(libs.room.ktx)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}
