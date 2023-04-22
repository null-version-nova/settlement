package org.nullversionnova.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.beust.klaxon.Klaxon
import org.nullversionnova.common.Identifier

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
        val data = try {
            Klaxon().parse<SpriteAnimation>(Gdx.files.internal("client/${identifier.pack}/spritedata/${identifier.name}.json").readString())
        } catch (e: Exception) {
            println("Warning: Exception while loading JSON associated with identifier $identifier")
            println(e)
            null
        }
        if (data != null) {
            for (i in data.sprites) {
                if (textureSet[Identifier(i)] == null) {
                    try {
                        textureSet[Identifier(i)] = Texture("client/${Identifier(i).pack}/sprites/${Identifier(i).name}.png")
                    } catch (e: Exception) {
                        println("Error: Exception while loading texture $i")
                        println(e)
                    }
                }
            }
        }
    }

    // Retrieving
    fun getTexture(identifier: Identifier) : Texture {
        return if (textureSet[identifier] == null) {
            getTexture(Identifier("engine","default")) }
        else { textureSet[identifier]!! }
    }

    // Getting
    fun getTextureSet() : Set<Identifier> { return textureSet.keys }
}
