package org.nullversionnova.world.tiles

import org.nullversionnova.registry.Identifier
import org.nullversionnova.ServerRegistry

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
