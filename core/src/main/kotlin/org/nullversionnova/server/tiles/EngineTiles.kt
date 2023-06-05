package org.nullversionnova.server.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry

object EngineTiles {
    // Tiles
    val NULL_TILE = Tile()
    val AIR = Tile().addProperty(Identifier("engine","transparent")).addProperty(Identifier("engine","intangible"))

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Identifier("engine","null"), NULL_TILE)
        registry.addTile("engine:air", AIR)
    }
}
