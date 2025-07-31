@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iliasahmed.jvm.library)
}
dependencies {
    implementation(libs.gson)
}
true