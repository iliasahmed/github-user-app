@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.iliasahmed.android.library)
    alias(libs.plugins.iliasahmed.android.library.compose)
}

android {
    namespace = "com.iliasahmed.ui"
}

dependencies {
    implementation(projects.core.designsystem)


    api(libs.androidx.corektx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    debugApi(libs.androidx.compose.ui.tooling)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.extjunit)
    androidTestImplementation(libs.test.espresso)
}