package org.nullversionnova.server.base.tiles

object BaseTileMaterials {
    fun ROCK() : TileProperties { return TileProperties("rock").setHardness(10) }
    fun SOIL() : TileProperties { return TileProperties("soil").setHardness(1) }
    fun ATMOSPHERE() : TileProperties { return TileProperties("atmosphere").isGas() }
}
