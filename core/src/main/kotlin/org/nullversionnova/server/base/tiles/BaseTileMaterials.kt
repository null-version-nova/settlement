package org.nullversionnova.server.base.tiles

import tiles.Tile

object BaseTileMaterials {
    fun ROCK() : Tile { return Tile("rock").setHardness(10) }
    fun SOIL() : Tile { return Tile("soil").setHardness(1) }
    fun ATMOSPHERE() : Tile { return Tile("atmosphere").isGas() }
}
