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
    fun sortMap() { tilemap.sortWith(compareBy({ it.cornerA.x },{ it.cornerA.y },{ it.cornerA.z })) }
    fun checkAllInBounds(tileGroup: TileGroup3) : MutableSet<IntegerVector3> {
        val set = mutableSetOf<IntegerVector3>()
        for (i in 0 until CELL_SIZE_X) {
            if (i > lowerNumber(tileGroup.cornerA.x,tileGroup.cornerB.x) && i < higherNumber(tileGroup.cornerA.x,tileGroup.cornerB.x)) {
                for (j in 0 until CELL_SIZE_Y) {
                    if (i > lowerNumber(tileGroup.cornerA.y,tileGroup.cornerB.y) && i < higherNumber(tileGroup.cornerA.y,tileGroup.cornerB.y)) {
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
        for (j in 0 until tilemap.size) {
            if (position.x >= lowerNumber(tilemap[j].cornerA.x,tilemap[j].cornerB.x) && position.x <= higherNumber(tilemap[j].cornerA.x,tilemap[j].cornerB.x)) {
                candidates.add(j)
            }
        }
        for (j in 0 until tilemap.size) {
            if (position.y <= lowerNumber(tilemap[j].cornerA.y,tilemap[j].cornerB.y) || position.y >= higherNumber(tilemap[j].cornerA.y,tilemap[j].cornerB.y)) {
                candidates.remove(j)
            }
        }
        for (j in 0 until tilemap.size) {
            if (position.z <= lowerNumber(tilemap[j].cornerA.z,tilemap[j].cornerB.z) || position.z >= higherNumber(tilemap[j].cornerA.z,tilemap[j].cornerB.z)) {
                candidates.remove(j)
            }
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
    fun lowerNumber(first: Int, second: Int) : Int {
        return if (first > second) {
            first
        } else {
            second
        }
    }
    fun higherNumber(first: Int, second: Int) : Int {
        return if (first < second) {
            first
        } else {
            second
        }
    }

    // Companions
    companion object Global {
        const val CELL_SIZE_X = 32
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
    }
}
