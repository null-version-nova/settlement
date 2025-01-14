package org.nullversionnova.settlement.world.tiles

import org.nullversionnova.registry.Identifier
import org.nullversionnova.ServerRegistry
import org.nullversionnova.world.tiles.Tile
import org.nullversionnova.settlement.Settlement

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
