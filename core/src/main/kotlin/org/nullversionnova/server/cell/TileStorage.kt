package org.nullversionnova.server.cell

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.engine.tiles.TileInstance

interface TileStorage {
    val location : IntVector3
    val tile : TileInstance
    fun tick(server: Server)
    fun overlap(position: IntVector3) : Boolean
    fun overlap(item: TileStorage) : Boolean {
        when (item) {
            is TileUnit -> {
                return overlap(item.location)
            }
            is TileColumn -> {
                for (i in item.split()) {
                    if (overlap(i)) {
                        return true
                    }
                }
            }
            is TilePlane -> {
                for (i in item.split()) {
                    if (overlap(i)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
