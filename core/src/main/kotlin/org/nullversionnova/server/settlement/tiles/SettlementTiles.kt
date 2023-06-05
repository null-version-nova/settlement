package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.tiles.Tile
import org.nullversionnova.server.settlement.Settlement

object SettlementTiles {
    // Tiles
    val ROCK = Tile(Settlement.getId("rock"))
    val DIRT = Soil(Settlement.getId("soil"))
    val GRASS = Tile(Settlement.getId("organic")).addProperty(Identifier("engine","always_floor"))
    val SAND = Tile(Settlement.getId("soil"))

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Settlement.getId("rock"), ROCK)
        registry.addTile(Settlement.getId("sand"), SAND)
        registry.addTile(Settlement.getId("dirt"), DIRT)
        registry.addTile(Settlement.getId("grass"), GRASS)
    }
}
