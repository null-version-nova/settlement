package org.nullversionnova.settlement.world.tiles

import org.nullversionnova.Registries
import org.nullversionnova.world.tiles.Tile

object SettlementTiles {
    // Tiles
    val ROCK by Registries.tileRegistry.register("settlement:rock") { Tile() }
    val DIRT by Registries.tileRegistry.register("settlement:dirt") { Tile() }
    val SAND by Registries.tileRegistry.register("settlement:sand") { Tile() }

    // Functions
    fun registerTiles() {
    }
}
