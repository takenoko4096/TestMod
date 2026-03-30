import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.20"
    id("net.fabricmc.fabric-loom")
    id("maven-publish")
}

version = "1.0-SNAPSHOT"
group = "com.gmail.takenokoii78"

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val javaVersion = 25

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
    }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("testmod") {
            sourceSet("main")
            sourceSet("client")
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")

    implementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    implementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}")

    implementation(files(
        "../Starlight/build/libs/Starlight-1.0-SNAPSHOT.jar"
    ))
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraft_version", project.property("minecraft_version"))
        inputs.property("loader_version", project.property("loader_version"))
        inputs.property("kotlin_loader_version", project.property("kotlin_loader_version"))
        inputs.property("fabric_api_version", project.property("fabric_api_version"))
        inputs.property("java_version", javaVersion)
        inputs.property("starlight_version", project.property("starlight_version"))
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version,
                "minecraft_version" to project.property("minecraft_version")!!,
                "loader_version" to project.property("loader_version")!!,
                "kotlin_loader_version" to project.property("kotlin_loader_version")!!,
                "fabric_api_version" to project.property("fabric_api_version")!!,
                "java_version" to javaVersion.toString(),
                "starlight_version" to project.property("starlight_version")!!
            )
        }
    }

    withType<JavaCompile>().configureEach {
        // ensure that the encoding is set to UTF-8, no matter what the system default is
        // this fixes some edge cases with special characters not displaying correctly
        // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
        // If Javadoc is generated, this must be specified in that task too.
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName}" }
        }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
