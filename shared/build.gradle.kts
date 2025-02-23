plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

group = "com.pedektech"
version = "1.0.0"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosArm64(),           // Real iOS devices
        iosSimulatorArm64()   // Modern simulators on Apple Silicon
    ).forEach {
        it.binaries.framework {
            baseName = "Encryption" // Match your library’s purpose
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Add multiplatform dependencies here if needed
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.pedektech.encryption"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ayetolusamuel/Encryption")
            credentials {
                username = project.findProperty("GITHUB_USERNAME") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("GITHUB_TOKEN") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("release") {
            from(components["kotlin"]) // Publishes all KMM targets
        }
    }
}