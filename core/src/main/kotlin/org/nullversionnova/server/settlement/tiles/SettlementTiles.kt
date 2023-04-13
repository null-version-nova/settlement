package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.settlement.Settlement

object SettlementTiles {
    // Tiles
    val ROCK = SettlementTileMaterials.ROCK()
    val SAND = SettlementTileMaterials.SOIL()

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Settlement.getId("rock"), ROCK)
        registry.addTile(Settlement.getId("sand"), SAND)
    }
}
