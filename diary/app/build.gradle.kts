
plugins {
    id("application")
    id("java")
}

group = "cc.diary"

repositories {
    mavenCentral()

    maven {
        url = uri("https://jogamp.org/deployment/maven")
    }
}

dependencies {
    implementation("org.processing:core:4.3.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "cc.diary.Main"
}