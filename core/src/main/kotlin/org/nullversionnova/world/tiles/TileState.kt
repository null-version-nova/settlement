package org.nullversionnova.world.tiles

import org.nullversionnova.registry.Identifier
import org.nullversionnova.Server

class TileState(instance: TileInstance, val tile: Tile) {
    companion object {
        fun getTileState(instance: TileInstance, server: Server) : TileState {
            return TileState(instance, server.registry.accessTile(instance.tileType.identifier))
        }
    }
    val isWall = !instance.isFloor && !tile.checkTileProperty(Identifier("engine","intangible"))
}
