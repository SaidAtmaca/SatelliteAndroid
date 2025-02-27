plugins {
    id("satellite.android.library")
    id("satellite.hilt")
    id("satellite.android.room")
}
android {
    namespace = "com.example.domain"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}