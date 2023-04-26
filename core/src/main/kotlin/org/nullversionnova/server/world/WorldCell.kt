package org.nullversionnova.server.world

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.tiles.TickableTile
import org.nullversionnova.server.tiles.TileInstance

class WorldCell (val location: IntVector3) {
    // Members
    private val tileMap = mutableMapOf<IntVector3, TileInstance>()
    private var loaded = false

    // Methods
    fun unload() {
        loaded = false
        tileMap.clear()
    }
    fun tick(server: Server) {
        for (i in tileMap.values) {
            val tile = server.registry.accessTile(i.identifier)
            if (tile is TickableTile) {
                tile.tick(i.location,server)
            }
        }
    }
    operator fun get(location: IntVector3): TileInstance? {
        return tileMap[location]
    }
    operator fun set(location: IntVector3, tile: TileInstance) { tileMap[location] = tile }

    // Companions
    companion object {
        const val CELL_SIZE = 32
    }
}
