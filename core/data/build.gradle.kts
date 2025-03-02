plugins {
    id("satellite.android.library")
    id("satellite.hilt")
    id("satellite.android.room")
}

android {
    namespace = "com.example.data"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.gson)

    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.junit.jupiter.api)
    testImplementation (libs.junit.jupiter.engine)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation ("com.google.truth:truth:1.4.4")
    testImplementation(libs.androidx.room.testing)

    androidTestImplementation (libs.mockito.core)
    androidTestImplementation (libs.mockito.kotlin)
    //androidTestImplementation (libs.junit.jupiter.api)
    //androidTestImplementation (libs.junit.jupiter.engine)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation ("com.google.truth:truth:1.4.4")
    androidTestImplementation (libs.kotlinx.coroutines.test)

}