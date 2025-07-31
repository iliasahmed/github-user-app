@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.iliasahmed.android.library)
    alias(libs.plugins.iliasahmed.android.hilt)
    alias(libs.plugins.iliasahmed.android.retrofit)
}
android {
    namespace = "com.iliasahmed.di"
}
dependencies {
    api(libs.log.timber)
    api(libs.bundles.network)
}
