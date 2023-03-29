package org.nullversionnova.server

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3
import org.nullversionnova.server.base.Base
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell {
    // Members
    val tilemap = mutableListOf(TileGroup(IntegerVector3(0,0,0),
        IntegerVector3(CELL_SIZE_X, CELL_SIZE_Y, CELL_SIZE_Z), Identifier("rock")))
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun sortMap() {
        for (i in tilemap) {
            
        }
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 32
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
