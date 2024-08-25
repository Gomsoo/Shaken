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

    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
