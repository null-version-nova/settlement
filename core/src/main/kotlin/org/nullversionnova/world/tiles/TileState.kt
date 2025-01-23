package org.nullversionnova.world.tiles

import org.nullversionnova.registry.Identifier

class TileState(instance: TileInstance, val tile: Tile) {
    companion object {
        fun getTileState(instance: TileInstance) : TileState {
            return TileState(instance, instance.tileType)
        }
    }
    val isWall = !instance.isFloor && !tile.checkTileProperty(Identifier("engine","intangible"))
}
