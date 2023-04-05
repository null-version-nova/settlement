package org.nullversionnova.data

import org.nullversionnova.server.WorldCell

data class TileGroup3(val location: IntegerVector3, val scale: IntegerVector3, var identifier: Identifier) {
    fun checkInBoundsOnAxis(axis: Int, depth: Int) : Boolean {
        return depth >= location.getAxisFromInt(axis) && depth <= location.getAxisFromInt(axis) + scale.getAxisFromInt(axis)
    }
    fun checkAllInBounds() : MutableSet<IntegerVector3> {
        val set = mutableSetOf<IntegerVector3>()
        for (i in 0 until WorldCell.CELL_SIZE_X) {
            if (checkInBoundsOnAxis(0,i)) {
                for (j in 0 until WorldCell.CELL_SIZE_Y) {
                    if (checkInBoundsOnAxis(1,j)) {
                        for (k in 0 until WorldCell.CELL_SIZE_Z) {
                            set.add(IntegerVector3(i,j,k))
                        }
                    }
                }
            }
        }
        return set
    }
    fun slice(direction: Int) : TileGroup2 {
        val location2 = IntegerVector2(0,0)
        val scale2 = IntegerVector2(0,0)
        when (direction) {
            0 -> {
                location2.x = location.y
                location2.y = location.z
                scale2.x = scale.y
                scale2.y = scale.z
            }
            1 -> {
                location2.x = location.x
                location2.y = location.z
                scale2.x = scale.x
                scale2.y = scale.z
            }
            else -> {
                location2.x = location.x
                location2.y = location.y
                scale2.x = scale.x
                scale2.y = scale.y
            }
        }
        return TileGroup2(location2, scale2, identifier)
    }

}
