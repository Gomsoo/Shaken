plugins {
    alias(libs.plugins.shaken.android.application)
    alias(libs.plugins.shaken.android.application.compose)
    alias(libs.plugins.shaken.hilt)
}

android {
    namespace = "com.gomsoo.shaken"

    defaultConfig {
        applicationId = "com.gomsoo.shaken"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)

    ksp(libs.hilt.compiler)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    kspTest(libs.hilt.compiler)

    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.hilt.android.testing)

    androidTestImplementation(platform(libs.androidx.compose.bom))
}
