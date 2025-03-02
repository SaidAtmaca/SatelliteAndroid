plugins {
    id("satellite.android.library")
    id("satellite.android.library.compose")
    id("satellite.hilt")
}

android {
    namespace = "com.example.presentation"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:viewModel"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.material.icons.extended)

    //navigation
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.androidx.core.ktx.v150)
    testImplementation(libs.androidx.junit.ktx.v115)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation("com.google.truth:truth:1.1.5")
    testImplementation(libs.mockito.core.v520)
    testImplementation(libs.mockito.kotlin.v521)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    testImplementation(libs.mockito.inline)


}