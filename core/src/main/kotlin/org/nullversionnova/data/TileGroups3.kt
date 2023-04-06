package org.nullversionnova.data

import org.nullversionnova.server.WorldCell

data class TileGroups3(val group: MutableList<TileGroup3> = mutableListOf()) {
    fun findTileGroup(position: IntegerVector3) : TileGroup3? {
        val candidates = mutableSetOf<TileGroup3>()
        for (i in findAllInPlane(0, position.x)) {
            candidates.add(i)
        }
        candidates.removeIf{!it.checkInBoundsOnAxis(1,position.y)}
        candidates.removeIf{!it.checkInBoundsOnAxis(2,position.z)}
        return if (candidates.isEmpty()) {
             null
        } else {
            candidates.first()
        }
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
    fun changeTile(location : IntegerVector3, identifier: Identifier) : Boolean {
        if (location.x < 0 || location.y < 0 || location.z < 0 ||
            location.x > WorldCell.CELL_SIZE_X || location.y > WorldCell.CELL_SIZE_Y || location.z > WorldCell.CELL_SIZE_Z) { return false }
        val oldGroup = findTileGroup(location)
        return if (oldGroup != null) {
            val i0 = oldGroup.identifier
            val x0 = oldGroup.location.x
            val y0 = oldGroup.location.y
            val z0 = oldGroup.location.z
            val x1 = oldGroup.scale.x
            val y1 = oldGroup.scale.y
            val z1 = oldGroup.scale.z
            if (i0 == identifier) {
                false
            } else {
                if (x1 == 1 && y1 == 1 && z1 == 1) {
                    oldGroup.identifier = identifier
                    return true
                }
                when (location.z) {
                    z0 -> {
                        group.add(TileGroup3(oldGroup.location.copy(z = z0 + 1),oldGroup.scale.copy(z = z1 - 1),i0))
                    }
                    z0 + z1 - 1 -> {
                        group.add(TileGroup3(oldGroup.location,oldGroup.scale.copy(z = z1 - 1),i0))
                    }
                    else -> {
                        group.add(TileGroup3(oldGroup.location,oldGroup.scale.copy(z = location.z - z0),i0))
                        group.add(TileGroup3(oldGroup.location.copy(z = location.z + 1),oldGroup.scale.copy(z = z1 - (location.z - z0)),i0))
                    }
                }
                when (location.x) {
                    x0 -> {
                        group.add(TileGroup3(oldGroup.location.copy(x = x0 + 1, z = location.z),oldGroup.scale.copy(x = x1 - 1, z = 1),i0))
                    }
                    x0 + x1 - 1 -> {
                        group.add(TileGroup3(oldGroup.location.copy(z = location.z),oldGroup.scale.copy(x = x1 - 1, z = 1),i0))
                    }
                    else -> {
                        group.add(TileGroup3(oldGroup.location.copy(z = location.z),oldGroup.scale.copy(x = location.x - x0 - 1, z = 1),i0))
                        group.add(TileGroup3(oldGroup.location.copy(x = location.x + 1, z = location.z),oldGroup.scale.copy(x = x1 - (location.x - x0), z = 1),i0))
                    }
                }
                when (location.y) {
                    y0 -> {
                        group.add(TileGroup3(oldGroup.location.copy(x = location.x, y = y0 + 1, z = location.z),oldGroup.scale.copy(x = 1, y = y1 - 1, z = 1),i0))
                    }
                    y0 + y1 - 1 -> {
                        group.add(TileGroup3(oldGroup.location.copy(x = location.x, z = location.z),oldGroup.scale.copy(x = 1, y = y1 - 1, z = 1),i0))
                    }
                    else -> {
                        group.add(TileGroup3(oldGroup.location.copy(x = location.z, z = location.z),oldGroup.scale.copy(x = 1, y = location.y - y0 - 1, z = 1),i0))
                        group.add(TileGroup3(oldGroup.location.copy(x = 1 ,y = location.y + 1, z = location.z),oldGroup.scale.copy(x = 1, y = y1 - (location.y - y0) - 1, z = 1),i0))
                    }
                }
                group.remove(oldGroup)
                true
            }
        } else {
            group.add(TileGroup3(location,IntegerVector3(1,1,1),identifier))
            true
        }
    }
}
