package org.nullversionnova.settlement.world.tiles

import org.nullversionnova.ServerRegistry
import org.nullversionnova.settlement.Settlement
import org.nullversionnova.world.tiles.Tile

object SettlementTiles {
    // Tiles
    val ROCK = Tile(Settlement.getId("rock"))
    val DIRT = Soil(Settlement.getId("soil"))
    val SAND = Tile(Settlement.getId("soil"))

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Settlement.getId("rock"), ROCK)
        registry.addTile(Settlement.getId("sand"), SAND)
        registry.addTile(Settlement.getId("dirt"), DIRT)
    }
}
