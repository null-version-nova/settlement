package org.nullversionnova.server.cell

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.engine.tiles.TickableTile
import org.nullversionnova.server.engine.tiles.TileInstance

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
