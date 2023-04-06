package org.nullversionnova.data

data class TileGroups2(val group: MutableList<TileGroup2>) {
    fun findAllInPlane(axis: Int, depth: Int) : MutableSet<TileGroup2> {
        val candidates = mutableSetOf<TileGroup2>()
        for (i in group) {
            if (i.checkInBoundsOnAxis(axis,depth)) {
                candidates.add(i)
            }
        }
        return candidates
    }
    fun findTileGroup(position: IntegerVector2) : TileGroup2? {
        val candidates = mutableSetOf<TileGroup2>()
        for (i in findAllInPlane(0, position.x)) { candidates.add(i) }
        candidates.removeIf { !it.checkInBoundsOnAxis(1,position.y) }
        if (candidates.isEmpty()) { return null }
        return candidates.first()
    }
    fun listAllTilesInGroup() : MutableSet<Identifier> {
        val allTiles = mutableSetOf<Identifier>()
        for (i in group) { allTiles.add(i.identifier) }
        return allTiles
    }
}
