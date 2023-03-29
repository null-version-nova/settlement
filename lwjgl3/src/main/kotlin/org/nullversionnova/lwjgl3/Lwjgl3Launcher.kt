@file:JvmName("Lwjgl3Launcher")

package org.nullversionnova.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import org.nullversionnova.client.Client

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(Client(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("settlement-kotlin")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
