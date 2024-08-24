plugins {
    alias(libs.plugins.shaken.android.library)
    alias(libs.plugins.shaken.android.library.compose)
}

android {
    namespace = "com.gomsoo.shaken.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
