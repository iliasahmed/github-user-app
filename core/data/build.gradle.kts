@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iliasahmed.android.library)
    alias(libs.plugins.iliasahmed.android.hilt)
    alias(libs.plugins.iliasahmed.android.test)
}
android {
    namespace = "com.iliasahmed.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.di)
    api(projects.model.apiresponse)
}