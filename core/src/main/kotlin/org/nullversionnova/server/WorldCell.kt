package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.TileGroup3
import org.nullversionnova.data.TileGroups3
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell {
    // Members
    val tilemap = TileGroups3(mutableListOf(TileGroup3(
        IntegerVector3(0,0,0),
        IntegerVector3(CELL_SIZE_X, CELL_SIZE_Y, CELL_SIZE_Z), Identifier("air")
    )))
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {

    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 64
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
