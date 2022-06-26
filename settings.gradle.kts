rootProject.name = "danger-detekt-kotlin"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

includeBuild("build-logic")

include(":plugin")
