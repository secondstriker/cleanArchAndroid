pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "Insurance Comparator"
//DataLayer
include(":app")

//For dependency management, we use a gradle plugin
includeBuild("gradlePlugins")
include(":domain")
include(":common")
include(":data")
include(":remote")
include(":presentation")
include(":commonandroid")
include(":features")
