package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.tiles.TileProperties
import org.nullversionnova.server.settlement.Settlement

object SettlementTiles {
    // Templates
    fun ROCK() : TileProperties { return TileProperties(Identifier("settlement","rock")).setHardness(10) }
    fun SOIL() : TileProperties { return TileProperties(Identifier("settlement","soil")).setHardness(1) }
    fun ATMOSPHERE() : TileProperties { return TileProperties(Identifier("settlement","atmosphere")).isGas() }

    // Tiles
    val ROCK = ROCK()
    val SAND = SOIL()

    // Functions
    fun registerTiles(registry: ServerRegistry) {
        registry.addTile(Settlement.getId("rock"), ROCK)
        registry.addTile(Settlement.getId("sand"), SAND)
    }
}
