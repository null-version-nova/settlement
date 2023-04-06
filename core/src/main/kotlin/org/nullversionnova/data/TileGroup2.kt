package org.nullversionnova.data

data class TileGroup2(val location: IntegerVector2, val scale: IntegerVector2, val identifier: Identifier) {
    fun checkInBoundsOnAxis(axis: Int, depth: Int) : Boolean {
        return depth >= location.getAxisFromInt(axis) && depth <= location.getAxisFromInt(axis) + scale.getAxisFromInt(axis) - 1
    }
}
