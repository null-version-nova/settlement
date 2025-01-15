package org.nullversionnova.registry

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class TextureRegistry(private val registry: MutableRegistry<TextureAtlas.AtlasRegion>
) : Registry<TextureAtlas.AtlasRegion> by registry {
    private var atlas : TextureAtlas? = null
    override fun register() {
        atlas = TextureAtlas(Gdx.files.internal(".packedimages/settlement.atlas"))
        val atlas = TextureAtlas(Gdx.files.internal(".packedimages/settlement.atlas"))
        atlas.regions.forEach {
            registry.register(Identifier(it.name.substringBefore('.'),it.name.substringAfter('.')),it)
        }
    }
    override fun dispose() {
        atlas?.dispose()
    }
}
