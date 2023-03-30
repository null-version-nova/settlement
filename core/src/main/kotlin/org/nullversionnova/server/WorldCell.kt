package org.nullversionnova.server

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector2
import org.nullversionnova.IntegerVector3
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell {
    // Members
    val tilemap = mutableListOf(TileGroup3(IntegerVector3(0,0,0),
        IntegerVector3(CELL_SIZE_X, CELL_SIZE_Y, CELL_SIZE_Z), Identifier("rock")))
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun checkAllInBounds(tileGroup: TileGroup3) : MutableSet<IntegerVector3> {
        val set = mutableSetOf<IntegerVector3>()
        for (i in 0 until CELL_SIZE_X) {
            if (i > tileGroup.getLesserOnAxis(0).x && i < tileGroup.getGreaterOnAxis(0).x) {
                for (j in 0 until CELL_SIZE_Y) {
                    if (j > tileGroup.getLesserOnAxis(1).y && j < tileGroup.getGreaterOnAxis(1).y) {
                        for (k in 0 until CELL_SIZE_Z) {
                            set.add(IntegerVector3(i,j,k))
                        }
                    }
                }
            }
        }
        return set
    }
    fun findTileGroup(position: IntegerVector3) : Int {
        val candidates = mutableSetOf<Int>()
        for (i in findAllInPlane(0, position.x)) {
            candidates.add(tilemap.indexOf(i))
        }
        for (i in 0 until tilemap.size) {
            if (position.y <= tilemap[i].getLesserOnAxis(1).y || position.y >= tilemap[i].getGreaterOnAxis(1).y) { candidates.remove(i) }
        }
        for (i in 0 until tilemap.size) {
            if (position.z <= tilemap[i].getLesserOnAxis(2).z || position.z >= tilemap[i].getGreaterOnAxis(2).z) { candidates.remove(i) }
        }
        return candidates.first()
    }
    fun slice(tileGroup: TileGroup3, direction: Int) : TileGroup2 {
        val cornerA = IntegerVector2(0,0)
        val cornerB = IntegerVector2(0,0)
        when (direction) {
            0 -> {
                cornerA.x = tileGroup.cornerA.y
                cornerA.y = tileGroup.cornerA.z
                cornerB.x = tileGroup.cornerB.y
                cornerB.y = tileGroup.cornerB.z
            }
            1 -> {
                cornerA.x = tileGroup.cornerA.x
                cornerA.y = tileGroup.cornerA.z
                cornerB.x = tileGroup.cornerB.x
                cornerB.y = tileGroup.cornerB.z
            }
            else -> {
                cornerA.x = tileGroup.cornerA.x
                cornerA.y = tileGroup.cornerA.y
                cornerB.x = tileGroup.cornerB.x
                cornerB.y = tileGroup.cornerB.y
            }
        }
        return TileGroup2(cornerA, cornerB, tileGroup.identifier)
    }
    fun findAllInPlane(axis: Int, depth: Int) : MutableSet<TileGroup3> {
        val candidates = mutableSetOf<TileGroup3>()
        for (j in tilemap) {
            if (depth >= j.getLesserOnAxis(axis).getAxisFromInt(axis) && depth <= j.getGreaterOnAxis(axis).getAxisFromInt(axis)) { candidates.add(j) }
        }
        return candidates
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 32
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
