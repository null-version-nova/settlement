package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.Tile
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell (location: IntegerVector3) {
    // Members
    val tilemap = mutableSetOf<Tile>()
    val generator = Generator(location)
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {
        for (i in 0..CELL_SIZE_X) {
            for (j in 0..CELL_SIZE_Y) {
                for (k in 0..(CELL_SIZE_Z.toDouble() * generator.getHeight(i.toDouble(),j.toDouble())).toInt()/2 + 10) {
                    tilemap.add(Tile(IntegerVector3(i,j,k),Identifier("sand")))
                }
            }
        }
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 64
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
