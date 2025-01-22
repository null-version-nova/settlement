//eclipse.project.name = appName +"-core"

plugins {
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org") }
    mavenLocal()
    gradlePluginPortal()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
    implementation("com.badlogicgames.gdx:gdx:1.12.1")
    api("io.github.libktx:ktx-app:${rootProject.properties["ktxVersion"]}")
    api("io.github.libktx:ktx-assets:${rootProject.properties["ktxVersion"]}")
    api("io.github.libktx:ktx-graphics:${rootProject.properties["ktxVersion"]}")
    api("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.properties["kotlinVersion"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    testImplementation(kotlin("test"))
    testImplementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:${rootProject.properties["gdxVersion"]}")
    testImplementation("com.badlogicgames.gdx:gdx-platform:${rootProject.properties["gdxVersion"]}:natives-desktop")
    testImplementation("com.badlogicgames.gdx:gdx-tools:${rootProject.properties["gdxVersion"]}")
}

tasks.test {
    useJUnitPlatform()
}


