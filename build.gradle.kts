plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "eu.sentinalcoding"
version = "1.0.0"

repositories {
    mavenCentral()
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven("http://nexus.devsrsouza.com.br/repository/maven-public/") {
        this.isAllowInsecureProtocol = true
    }
    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("com.okkero.skedule:skedule:1.2.6")
    implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:4.0.0")
}
