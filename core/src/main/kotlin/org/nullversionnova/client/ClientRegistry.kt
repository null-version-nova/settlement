package org.nullversionnova.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.beust.klaxon.Klaxon
import org.nullversionnova.Identifier

class ClientRegistry {
    // Initializing and Finalizing
    fun initialize() {
        missingTexture = Texture(Gdx.files.internal("default.png"))
        println("Client registry initialized!")
    }
    fun destroy() {
        for (i in textureSet) { i.value.dispose() }
        missingTexture.dispose()
        println("Client registry finalized!")
    }

    // Members
    private lateinit var missingTexture : Texture

    // Registries
    private val textureSet = mutableMapOf<Identifier, Texture>()

    // Loading
    fun loadTextureFromTile(identifier: Identifier) {
        val data = Klaxon().parse<TileTextureData>(Gdx.files.internal("${identifier.pack}/models/tiles/${identifier.name}.json").readString())
        if (data == null) {
            return
        }
        else {
            loadTextureFromData(data.top)
            if (data.top != data.side) {
                loadTextureFromData(data.side)
            }
            if (data.side != data.bottom) {
                loadTextureFromData(data.bottom)
            }
        }
    }
    fun loadTextureFromData(identifier: Identifier) {
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
        return if (textureSet[identifier] == null) { missingTexture }
        else { textureSet[identifier]!! }
    }

    // Getting
    fun getTextureSet() : Set<Identifier> { return textureSet.keys }
}
