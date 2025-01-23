package org.nullversionnova.world.tiles

import org.nullversionnova.Registries
import org.nullversionnova.registry.Identifier

object EngineTiles {
    // Tiles
    val NULL_TILE by Registries.tileRegistry.register("engine:null") { Tile() }
    val AIR by Registries.tileRegistry.register("engine:air") { Tile().addProperty(Identifier("engine","transparent")).addProperty(Identifier("engine","intangible")) }
}
