package org.nullversionnova.server

import org.nullversionnova.IntegerVector2

data class TileGroups2(val group: MutableList<TileGroup2>) {
    fun findAllInPlane(axis: Int, depth: Int) : MutableSet<TileGroup2> {
        val candidates = mutableSetOf<TileGroup2>()
        for (j in group) {
            if (depth >= j.getLesserOnAxis(axis).getAxisFromInt(axis) && depth <= j.getGreaterOnAxis(axis).getAxisFromInt(axis)) { candidates.add(j) }
        }
        return candidates
    }
    fun findTileGroup(position: IntegerVector2) : TileGroup2 {
        val candidates = mutableListOf<TileGroup2>()
        for (i in findAllInPlane(0, position.x)) { candidates.add(i) }
        with (candidates.iterator()) {
            forEach {
                if (position.y <= it.getLesserOnAxis(1).y || position.y >= it.getGreaterOnAxis(1).y) {
                    remove()
                }
            }
        }
        return candidates[0]
    }
    fun checkAllInBounds(tileGroup: TileGroup2) : MutableSet<IntegerVector2> {
        val set = mutableSetOf<IntegerVector2>()
        for (i in 0 until WorldCell.CELL_SIZE_X) {
            if (i > tileGroup.getLesserOnAxis(0).x && i < tileGroup.getGreaterOnAxis(0).x) {
                for (j in 0 until WorldCell.CELL_SIZE_Y) { set.add(IntegerVector2(i,j)) }
            }
        }
        return set
    }
}
