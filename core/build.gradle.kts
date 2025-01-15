//eclipse.project.name = appName +"-core"

dependencies {
    api("com.badlogicgames.gdx:gdx:${rootProject.properties["gdxVersion"]}")
    api("io.github.libktx:ktx-app:${rootProject.properties["ktxVersion"]}")
    api("io.github.libktx:ktx-assets:${rootProject.properties["ktxVersion"]}")
    api("io.github.libktx:ktx-graphics:${rootProject.properties["ktxVersion"]}")
    api("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.properties["kotlinVersion"]}")
    implementation("com.badlogicgames.gdx:gdx-tools:${rootProject.properties["gdxVersion"]}")
    testImplementation(kotlin("test"))
    testImplementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:${rootProject.properties["gdxVersion"]}")
    testImplementation("com.badlogicgames.gdx:gdx-platform:${rootProject.properties["gdxVersion"]}:natives-desktop")
    testImplementation("com.badlogicgames.gdx:gdx-tools:${rootProject.properties["gdxVersion"]}")
}

tasks.test {
    useJUnitPlatform()
}
