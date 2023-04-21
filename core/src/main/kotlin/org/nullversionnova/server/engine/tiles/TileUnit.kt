package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server

data class TileUnit(override val location : IntVector3, override val tile: TileInstance) : TileStorage {
    override fun tick(server: Server) {
        val tile = tile.getTile(server.registry) as TickableTile
        tile.tick(location, server)
    }
    override fun overlap(position: IntVector3): Boolean {
        return position == location
    }
    override fun overlap(item: TileStorage): Boolean {
        return item.overlap(location)
    }
}
