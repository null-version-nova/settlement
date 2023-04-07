package org.nullversionnova.server

import org.nullversionnova.SimplexNoise
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.data.Tile
import org.nullversionnova.server.base.entities.StaticEntity

class WorldCell (val location: IntegerVector3) {
    // Members
    val tilemap = mutableSetOf<Tile>()
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {
        println("Generating cell")
        tilemap.clear()
        for (i in 0..CELL_SIZE_X) {
            for (j in 0..CELL_SIZE_Y) {
                for (k in 0..CELL_SIZE_Z) {
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
    fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + location.z * CELL_SIZE_Z + Y_OFFSET
        return SimplexNoise.noise(xin.toDouble() / H_SCALE + location.x * CELL_SIZE_X, yin.toDouble() / H_SCALE + location.y * CELL_SIZE_Y) / V_SCALE * CELL_SIZE_Z + offset
    }


    // Companions
    companion object Global {
        const val CELL_SIZE_X = 128
        const val CELL_SIZE_Y = CELL_SIZE_X
        const val CELL_SIZE_Z = CELL_SIZE_X
        const val H_SCALE : Double = 200.0
        const val V_SCALE : Double = 10.0
        const val Y_OFFSET : Int = 20
        const val SOIL_DEPTH : Int = 3
    }
}
