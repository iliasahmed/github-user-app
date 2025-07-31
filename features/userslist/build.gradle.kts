@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iliasahmed.android.feature.compose)
    alias(libs.plugins.iliasahmed.android.hilt)
    alias(libs.plugins.iliasahmed.android.test)
}

android {
    namespace = "com.iliasahmed.userslist"
}
dependencies {
    implementation(libs.image.coil.compose)
    implementation(libs.androidx.activity)
    androidTestImplementation(libs.test.compose.ui.junit)
}