plugins {
    alias(libs.plugins.shaken.android.library)
    alias(libs.plugins.shaken.android.room)
    alias(libs.plugins.shaken.hilt)
}

android {
    namespace = "com.gomsoo.shaken.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.kotlinx.coroutines.test)
}
