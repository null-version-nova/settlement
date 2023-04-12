package org.nullversionnova.server

import org.nullversionnova.SimplexNoise
import org.nullversionnova.data.Axis
import org.nullversionnova.data.Axis.*
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.server.base.tiles.*

class WorldCell (private val location: IntegerVector3) {
    // Members
    val tilemap = mutableSetOf<TileStorage>()
    var loaded = false

    // Methods
    fun generate() {
        loaded = true
        tilemap.clear()
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                val height = getHeight(i.toDouble(),j.toDouble())
                addColumn(IntegerVector3(i,j,0), height.toInt(), Z, SolidTile(Identifier("rock")))
                addColumn(IntegerVector3(i,j,height.toInt()+1), SOIL_DEPTH, Z, SolidTile(Identifier("sand")))
            }
        }
    }
    fun unload() {
        loaded = false
        tilemap.clear()
    }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE ) / V_SCALE * CELL_SIZE + offset )
    }
    private fun addColumn(location: IntegerVector3, height: Int, axis: Axis, tile: TileInstance) {
        if (height < 0) return
        if (location.getAxis(axis) < 0) {
            if (location.getAxis(axis) + height > 0) {
//                tilemap.add(TileColumn(location.getNewWithSetAxis(-location.getAxis(axis),axis),height - location.getAxis(axis),axis,tile))
                return
            }
        } else if (location.getAxis(axis) < CELL_SIZE) {
            tilemap.add(TileColumn(location,height,axis,tile))
            return
        }
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
