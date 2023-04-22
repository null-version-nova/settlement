package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.engine.ServerRegistry

object EngineTiles {
    // Tiles
    val NULL_TILE = Tile()

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Identifier("engine","null"), NULL_TILE)
    }
}
