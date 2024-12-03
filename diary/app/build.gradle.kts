import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("application")
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "cc.diary"
var baseName = "jc-cc-diary"

repositories {
    mavenCentral()

    maven {
        url = uri("https://jogamp.org/deployment/maven")
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("org.processing:core:4.3.1")
    implementation("com.opencsv:opencsv:5.9")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}


application {
    mainClass = "cc.diary.Main"
}

// Set main class in MANIFEST.MF so we can do java -jar bleh.jar
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}

tasks.withType<ShadowJar> {
    // remove -all suffix
    archiveClassifier.set("")

    // set shadow'd jar filename
    archiveBaseName.set(baseName)
    
    // Enable relocation to (pkg).libs
    isEnableRelocation = true
    relocationPrefix = "${project.group}.libs"
}

tasks.withType<Jar>().named("sourcesJar") {
    archiveBaseName.set(baseName)
}