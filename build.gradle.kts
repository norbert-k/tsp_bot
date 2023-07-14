plugins {
    kotlin("jvm") version "1.8.20"
    id("org.flywaydb.flyway") version "9.8.1"
    application
}

group = "lv.tsp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.discord4j:discord4j-core:3.2.5")
    implementation("com.github.walkyst:lavaplayer-fork:1.4.2")
    implementation("com.beust:jcommander:1.82")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("lv.tsp.discordbot.Application")
}

flyway {
    url = "jdbc:sqlite:./db.sqlite"
    user = ""
    password = ""
}