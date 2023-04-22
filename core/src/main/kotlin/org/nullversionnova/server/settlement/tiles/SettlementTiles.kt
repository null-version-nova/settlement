package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.server.engine.ServerRegistry
import org.nullversionnova.server.engine.tiles.Tile
import org.nullversionnova.server.settlement.Settlement

object SettlementTiles {
    // Tiles
    val ROCK = Tile(Settlement.getId("rock"))
    val DIRT = Tile(Settlement.getId("soil"))
    val SAND = Tile(Settlement.getId("soil"))

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Settlement.getId("rock"), ROCK)
        registry.addTile(Settlement.getId("sand"), SAND)
        registry.addTile(Settlement.getId("dirt"), DIRT)
    }
}
