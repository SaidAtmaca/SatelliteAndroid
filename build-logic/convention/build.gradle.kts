import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation("com.android.tools.build:gradle:8.6.0")

}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}


gradlePlugin {
    plugins {

        register("androidApplicationCompose") {
            id = "satellite.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "satellite.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "satellite.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "satellite.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidRoom") {
            id = "satellite.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("hilt") {
            id = "satellite.hilt"
            implementationClass = "HiltConventionPlugin"
        }
    }
}