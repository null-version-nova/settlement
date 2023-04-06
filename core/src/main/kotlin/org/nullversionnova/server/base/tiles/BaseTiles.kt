package org.nullversionnova.server.base.tiles

import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.base.Base

object BaseTiles {
    // Tiles
    val ROCK = BaseTileMaterials.ROCK()
    val SAND = BaseTileMaterials.SOIL()

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Base.getId("rock"), ROCK)
        registry.addTile(Base.getId("sand"), SAND)
    }
}
