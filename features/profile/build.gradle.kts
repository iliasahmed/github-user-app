@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iliasahmed.android.feature.compose)
    alias(libs.plugins.iliasahmed.android.test)
}
android {
    namespace = "com.iliasahmed.profile"
}

dependencies {
    implementation(libs.image.coil.compose)
}