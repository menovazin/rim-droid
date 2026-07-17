pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "rim_droid"

include(":presentation")
include(":domain")
include(":data")
include(":core-test")
// Fork of SmartToolFactory/Compose-Zoom (multi-touch-only pan/consume). See compose-zoom/FORK.md.
include(":compose-zoom")
