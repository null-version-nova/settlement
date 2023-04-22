package org.nullversionnova.server.engine.cell

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Server
import org.nullversionnova.server.engine.tiles.TileInstance

data class TileCube(override val location : IntVector3, val height : Int, val width : Int, val depth: Int, override val tile : TileInstance) :
    TileStorage {
    override fun tick(server: Server) {
        TODO("Not yet implemented")
    }

    override fun overlap(position: IntVector3): Boolean {
        TODO("Not yet implemented")
    }

    override fun overlap(item: TileStorage): Boolean {
        TODO("Not yet implemented")
    }
}
