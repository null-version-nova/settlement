package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector2
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.TileGroup2
import org.nullversionnova.data.TileGroup3
import org.nullversionnova.data.TileGroups3
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell {
    // Members
    val tilemap = TileGroups3(mutableListOf(TileGroup3(
        IntegerVector3(0,0,0),
        IntegerVector3(CELL_SIZE_X, CELL_SIZE_Y, CELL_SIZE_Z), Identifier("rock")
    )))
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun checkAllInBounds(tileGroup: TileGroup3) : MutableSet<IntegerVector3> {
        val set = mutableSetOf<IntegerVector3>()
        for (i in 0 until CELL_SIZE_X) {
            if (tileGroup.checkInBoundsOnAxis(0,i)) {
                for (j in 0 until CELL_SIZE_Y) {
                    if (tileGroup.checkInBoundsOnAxis(1,j)) {
                        for (k in 0 until CELL_SIZE_Z) {
                            set.add(IntegerVector3(i,j,k))
                        }
                    }
                }
            }
        }
        return set
    }
    fun slice(tileGroup: TileGroup3, direction: Int) : TileGroup2 {
        val location = IntegerVector2(0,0)
        val scale = IntegerVector2(0,0)
        when (direction) {
            0 -> {
                location.x = tileGroup.location.y
                location.y = tileGroup.location.z
                scale.x = tileGroup.scale.y
                scale.y = tileGroup.scale.z
            }
            1 -> {
                location.x = tileGroup.location.x
                location.y = tileGroup.location.z
                scale.x = tileGroup.scale.x
                scale.y = tileGroup.scale.z
            }
            else -> {
                location.x = tileGroup.location.x
                location.y = tileGroup.location.y
                scale.x = tileGroup.scale.x
                scale.y = tileGroup.scale.y
            }
        }
        return TileGroup2(location, scale, tileGroup.identifier)
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 64
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
