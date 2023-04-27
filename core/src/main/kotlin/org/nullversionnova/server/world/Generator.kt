package org.nullversionnova.server.world

import org.nullversionnova.SimplexNoise
import org.nullversionnova.common.IntVector2
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server

class Generator {
    fun generateCell(cell: WorldCell, server: Server) {
        val heightmap = mutableMapOf<IntVector2,Int>()
        for (i in 0 until WorldCell.CELL_SIZE) {
            for (j in 0 until WorldCell.CELL_SIZE) {
                heightmap[IntVector2(i,j)] = (
                    SimplexNoise.noise(
                    (i.toDouble() + cell.location.x * WorldCell.CELL_SIZE) / H_SCALE,
                    (j.toDouble() + cell.location.y * WorldCell.CELL_SIZE) / H_SCALE
                ) / V_SCALE * WorldCell.CELL_SIZE).toInt() - WorldCell.CELL_SIZE * cell.location.z
            }
        }
        for (i in 0 until WorldCell.CELL_SIZE) {
            for (j in 0 until WorldCell.CELL_SIZE) {
                for (k in 0 until heightmap[IntVector2(i,j)]!! - SOIL_DEPTH) {
                    cell[IntVector3(i,j,k)] = server.registry.instanceTile("settlement:rock",cell.location.toGlobal(i,j,k),server)
                }
                for (k in heightmap[IntVector2(i,j)]!! - SOIL_DEPTH until heightmap[IntVector2(i,j)]!!) {
                    cell[IntVector3(i,j,k)] = server.registry.instanceTile("settlement:sand",cell.location.toGlobal(i,j,k),server)
                }
            }
        }
    }
    companion object {
        const val H_SCALE : Double = 50.0
        const val V_SCALE : Double = 5.0
        const val SOIL_DEPTH : Int = 3
    }
}
