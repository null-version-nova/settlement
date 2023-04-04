package org.nullversionnova.data

data class TileGroups3(val group: MutableList<TileGroup3>) {
    fun findTileGroup(position: IntegerVector3) : TileGroup3 {
        val candidates = mutableSetOf<TileGroup3>()
        for (i in findAllInPlane(0, position.x)) {
            candidates.add(i)
        }
        for (i in group) {
            if (!i.checkInBoundsOnAxis(1,position.y)) { candidates.remove(i) }
        }
        for (i in group) {
            if (!i.checkInBoundsOnAxis(2,position.z)) { candidates.remove(i) }
        }
        return candidates.first()
    }
    fun findAllInPlane(axis: Int, depth: Int) : MutableSet<TileGroup3> {
        val candidates = mutableSetOf<TileGroup3>()
        for (i in group) {
            if (i.checkInBoundsOnAxis(axis,depth)) {
                candidates.add(i)
            }
        }
        return candidates
    }
}
