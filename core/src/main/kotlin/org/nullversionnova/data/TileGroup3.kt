package org.nullversionnova.data

data class TileGroup3(val location: IntegerVector3, val scale: IntegerVector3, val identifier: Identifier) {
    fun checkInBoundsOnAxis(axis: Int, depth: Int) : Boolean {
        return depth >= location.getAxisFromInt(axis) && depth <= location.getAxisFromInt(axis) + scale.getAxisFromInt(axis)
    }
}
