package org.nullversionnova.server.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.Server

class TileState(val instance: TileInstance, val tile: Tile) {
    companion object {
        fun getTileState(instance: TileInstance, server: Server) : TileState {
            return TileState(instance, server.registry.accessTile(instance.identifier))
        }
    }
    val isWall = !instance.isFloor && !tile.checkTileProperty(Identifier("engine","intangible"))
}
