package org.nullversionnova.server

import org.nullversionnova.IntegerVector3

data class TileGroups3(val group: MutableList<TileGroup3>) {
    fun findTileGroup(position: IntegerVector3) : TileGroup3 {
        val candidates = mutableSetOf<TileGroup3>()
        for (i in findAllInPlane(0, position.x)) {
            candidates.add(i)
        }
        for (i in group) {
            if (position.y < i.getLesserOnAxis(1).y || position.y > i.getGreaterOnAxis(1).y) { candidates.remove(i) }
        }
        for (i in group) {
            if (position.z < i.getLesserOnAxis(2).z || position.z > i.getGreaterOnAxis(2).z) { candidates.remove(i) }
        }
        return candidates.first()
    }
    fun findAllInPlane(axis: Int, depth: Int) : MutableSet<TileGroup3> {
        val candidates = mutableSetOf<TileGroup3>()
        for (j in group) {
            if (depth >= j.getLesserOnAxis(axis).getAxisFromInt(axis) && depth <= j.getGreaterOnAxis(axis).getAxisFromInt(axis)) {
                candidates.add(j)
            }
        }
        return candidates
    }
}
