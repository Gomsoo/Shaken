plugins {
    alias(libs.plugins.shaken.android.feature)
    alias(libs.plugins.shaken.android.library.compose)
}

android {
    namespace = "com.gomsoo.shaken.feature.favorite"
}

dependencies {
    implementation(projects.core.data)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
