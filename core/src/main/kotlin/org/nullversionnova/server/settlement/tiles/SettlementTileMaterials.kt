package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.server.engine.tiles.TileProperties

object SettlementTileMaterials {
    fun ROCK() : TileProperties { return TileProperties("rock").setHardness(10) }
    fun SOIL() : TileProperties { return TileProperties("soil").setHardness(1) }
    fun ATMOSPHERE() : TileProperties { return TileProperties("atmosphere").isGas() }
}
