@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iliasahmed.android.library)
    alias(libs.plugins.iliasahmed.android.hilt)
    alias(libs.plugins.iliasahmed.android.test)
}

android {
    namespace = "com.iliasahmed.domain"
}

dependencies {
    api(projects.model.entity)
    implementation(libs.androidx.corektx)
    implementation(libs.kotlinx.coroutines.android)
}