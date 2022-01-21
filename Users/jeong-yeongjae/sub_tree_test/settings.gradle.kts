pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "KMM"
include(":app")
include(":data")
include(":domain")
include(":healthwatch")
include(":presentation")
include(":PoseEngine")