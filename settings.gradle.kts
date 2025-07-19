rootProject.name = "Etudes"

include("misc")
include("retrofittestdrive")

// see https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog-declaration
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            
            // ##########################################################
            // ## Versions
            // ##########################################################
            
            version("commons-math-ver", "3.6.1")
            version("kotest-ver", "5.9.1")
            
            // ##########################################################
            // ## Libraries
            // ##########################################################
            
            library("commons-math3", "org.apache.commons", "commons-math3").versionRef("commons-math-ver")
            
            library("kotest-runner-junit5", "io.kotest", "kotest-runner-junit5").versionRef("kotest-ver")
            library("kotest-assertions-core-jvm", "io.kotest", "kotest-assertions-core-jvm").versionRef("kotest-ver")
            library("kotest-property", "io.kotest", "kotest-property").versionRef("kotest-ver")
            library("kotest-framework-datatest", "io.kotest", "kotest-framework-datatest").versionRef("kotest-ver")
            
            bundle("kotest", listOf("kotest-runner-junit5", "kotest-assertions-core-jvm", "kotest-property", "kotest-framework-datatest"))
        }
    }
}