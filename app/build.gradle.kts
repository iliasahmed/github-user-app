import java.util.Properties

plugins {
    alias(libs.plugins.iliasahmed.android.application)
    alias(libs.plugins.iliasahmed.android.hilt)
    alias(libs.plugins.iliasahmed.android.application.compose)
}

val secretPropertiesFile: File = rootProject.file("app/secrets.properties")
val sitProps = Properties()
secretPropertiesFile.inputStream().use { input ->
    sitProps.load(input)
}

android {
    namespace = "com.iliasahmed.githubuserapp"
    defaultConfig {
        applicationId = "com.iliasahmed.compose"
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", sitProps.getProperty("API_KEY"))
        buildConfigField("String", "BASE_URL", sitProps.getProperty("BASE_URL"))
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}
dependencies {
    implementation(projects.core.di)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)

    implementation(projects.features.userslist)
    implementation(projects.features.profile)

    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.hilt.navigation)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.extjunit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.compose.ui.junit)
    debugImplementation(libs.androidx.compose.ui.manifest)
}