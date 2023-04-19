package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.engine.GameObject

data class TileUnit(override val location : IntVector3, override val tile: GameObject) : TileStorage {
    override fun tick(server: Server) {
        val tile = tile.getTile(server.registry) as TickableTile
        tile.tick(location, server)
    }
}
