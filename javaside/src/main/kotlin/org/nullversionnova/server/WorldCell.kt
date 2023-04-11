package org.nullversionnova.server

import org.nullversionnova.SimplexNoise
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.server.base.tiles.TileInstance
import org.nullversionnova.server.base.entities.StaticEntity
import org.nullversionnova.server.base.tiles.SolidTile

class WorldCell (private val location: IntegerVector3) {
    // Members
    val tilemap = mutableSetOf<TileInstance>()
    var loadedStaticEntities = mutableListOf<StaticEntity>()

    // Methods
    fun generate() {
        println("Generating cell")
        tilemap.clear()
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                for (k in 0 until CELL_SIZE) {
                    val height = getHeight(i.toDouble(),j.toDouble())
                    if (k < height) {
                        tilemap.add(SolidTile(IntegerVector3(i,j,k),Identifier("rock")))
                    }
                    else if (k < height + SOIL_DEPTH) {
                        tilemap.add(SolidTile(IntegerVector3(i,j,k),Identifier("sand")))
                    }
                }
            }
        }
    }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE ) / V_SCALE * CELL_SIZE + offset )
    }


    // Companions
    companion object Global {
        const val CELL_SIZE = 32
        const val H_SCALE : Double = 50.0
        const val V_SCALE : Double = 5.0
        const val Y_OFFSET : Int = 0
        const val SOIL_DEPTH : Int = 3
    }
}
