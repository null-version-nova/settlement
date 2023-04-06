package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.TileGroup3
import org.nullversionnova.data.TileGroups3
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell {
    // Members
    val tilemap = TileGroups3()
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {
        tilemap.group.add(TileGroup3(IntegerVector3(), IntegerVector3(64,32,64),Identifier("rock")))
        tilemap.group.add(TileGroup3(IntegerVector3(0,32,0), IntegerVector3(CELL_SIZE_X, CELL_SIZE_Y/2, 60), Identifier("sand")))
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 64
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
