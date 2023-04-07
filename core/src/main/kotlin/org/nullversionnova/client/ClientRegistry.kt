package org.nullversionnova.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.beust.klaxon.Klaxon
import org.nullversionnova.data.Identifier

class ClientRegistry {
    // Initializing and Finalizing
    fun initialize() {
        println("Client registry initialized!")
    }
    fun destroy() {
        for (i in textureSet) { i.value.dispose() }
        println("Client registry finalized!")
    }

    // Registries
    private val textureSet = mutableMapOf<Identifier, Texture>()

    // Loading
    fun loadTexture(identifier: Identifier) {
        val data = Klaxon().parse<SpriteAnimation>(Gdx.files.internal("${identifier.pack}/spritedata/${identifier.name}.json").readString())
        if (data != null) {
            for (i in data.sprites) {
                if (textureSet[i] == null) {
                    textureSet[i] = Texture("${i.pack}/sprites/${i.name}.png")
                }
            }
        }
    }

    // Retrieving
    fun getTexture(identifier: Identifier) : Texture {
        return if (textureSet[identifier] == null) { getTexture(Identifier("system","default")) }
        else { textureSet[identifier]!! }
    }

    // Getting
    fun getTextureSet() : Set<Identifier> { return textureSet.keys }
}
