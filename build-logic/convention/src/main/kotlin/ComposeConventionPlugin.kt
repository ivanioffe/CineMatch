import com.ioffeivan.cinematch.commonExtension
import com.ioffeivan.cinematch.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            commonExtension.apply {
                buildFeatures {
                    compose = true
                }

                dependencies {
                    "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
                    "implementation"(libs.findLibrary("androidx-ui").get())
                    "implementation"(libs.findLibrary("androidx-ui-graphics").get())
                    "implementation"(libs.findLibrary("androidx-ui-tooling-preview").get())
                    "implementation"(libs.findLibrary("androidx-material3").get())

                    "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
                }
            }
        }
    }
}
