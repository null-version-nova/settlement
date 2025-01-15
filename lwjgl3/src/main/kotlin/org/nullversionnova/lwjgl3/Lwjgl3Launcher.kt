@file:JvmName("Lwjgl3Launcher")

package org.nullversionnova.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings
import org.nullversionnova.client.Client

/** Launches the desktop (LWJGL3) application. */
fun main() {
    FileHandle(".unpackedimages").deleteDirectory()
    FileHandle(".packedimages").deleteDirectory()
    FileHandle("client").list().forEach { namespace ->
        namespace.child("sprites").list().forEach { file ->
            file.copyTo(FileHandle(".unpackedimages/${namespace.name()}.${file.name()}"))
        }
    }
    val settings = Settings()
    TexturePacker.process(settings,".unpackedimages",".packedimages","settlement")
    Lwjgl3Application(Client(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("client/settlement")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
