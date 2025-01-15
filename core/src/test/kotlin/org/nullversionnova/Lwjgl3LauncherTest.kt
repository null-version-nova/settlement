package org.nullversionnova

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun launchGdxRuntime(callback: () -> Unit) {
    Lwjgl3Application(TestClient(callback), Lwjgl3ApplicationConfiguration().apply {
        setTitle("client/settlement")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
