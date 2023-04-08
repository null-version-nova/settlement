package org.nullversionnova.server

import org.nullversionnova.SimplexNoise
import org.nullversionnova.data.Axis
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.Tile
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell (private val location: IntegerVector3) {
    // Members
    val tilemap = mutableSetOf<Tile>()
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {
        println("Generating cell")
        tilemap.clear()
        for (i in 0 until CELL_SIZE_X) {
            for (j in 0 until CELL_SIZE_Y) {
                for (k in 0 until CELL_SIZE_Z) {
                    val height = getHeight(i.toDouble(),j.toDouble())
                    if (k < height) {
                        tilemap.add(Tile(IntegerVector3(i,j,k),Identifier("rock")))
                    }
                    else if (k < height + SOIL_DEPTH) {
                        tilemap.add(Tile(IntegerVector3(i,j,k),Identifier("sand")))
                    }
                }
            }
        }
    }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE_Z
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE_X) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE_Y) / H_SCALE ) / V_SCALE * CELL_SIZE_Z + offset )
    }


    // Companions
    companion object Global {
        const val CELL_SIZE_X = 32
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
        const val H_SCALE : Double = 50.0
        const val V_SCALE : Double = 5.0
        const val Y_OFFSET : Int = 0
        const val SOIL_DEPTH : Int = 3
        fun getSizeFromAxis(axis: Axis) : Int {
            return when (axis) {
                Axis.X -> CELL_SIZE_X
                Axis.Y -> CELL_SIZE_Y
                Axis.Z -> CELL_SIZE_Z
            }
        }
    }
}
