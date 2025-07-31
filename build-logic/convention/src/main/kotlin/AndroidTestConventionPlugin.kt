import com.android.build.gradle.BaseExtension
import com.iliasahmed.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("hilt.test").get())
                "androidTestImplementation"(libs.findLibrary("hilt.test").get())
                "testImplementation"(libs.findLibrary("coroutines.test").get())
                "testImplementation"(libs.findLibrary("turbine").get())
            }

            // Add packaging excludes for all Android modules
            plugins.withId("com.android.library") {
                configurePackaging()
            }

            plugins.withId("com.android.application") {
                configurePackaging()
            }
        }
    }

    private fun Project.configurePackaging() {
        extensions.configure<BaseExtension> {
            packagingOptions {
                resources.excludes.addAll(
                    listOf(
                        "/META-INF/{AL2.0,LGPL2.1}",
                        "META-INF/LICENSE.md",
                        "META-INF/LICENSE-notice.md",
                        "META-INF/DEPENDENCIES",
                        "META-INF/NOTICE",
                        "META-INF/LICENSE"
                    )
                )
            }
        }
    }
}