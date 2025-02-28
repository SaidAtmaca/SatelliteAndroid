plugins {
    id("satellite.android.library")
    id("satellite.hilt")
}

android {
    namespace = "com.example.viewmodel"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
}