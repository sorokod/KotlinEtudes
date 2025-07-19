import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2

plugins {
    kotlin("jvm") version "2.2.0"
    java
}

val javaVersion = 23

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "org.xor"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.toVersion(javaVersion)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_23)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        compilerOptions.languageVersion.set(KOTLIN_2_2)
    }

    // Dependencies defined in individual module build files
}