package org.nullversionnova.server.engine.cell

import org.nullversionnova.SimplexNoise
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.ServerRegistry
import org.nullversionnova.server.engine.tiles.*

class WorldCell (private val location: IntVector3) {
    // Members
    private val tileMap = mutableMapOf<IntVector3, TileInstance>()
    private var loaded = false

    // Methods
    fun generate(registry: ServerRegistry) {
        loaded = true
        tileMap.clear()
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                val height = getHeight(i.toDouble(),j.toDouble()).toInt()
                addColumn(IntVector3(i,j,0), height, Identifier("settlement","rock"), registry)
                addColumn(IntVector3(i,j,height), SOIL_DEPTH, Identifier("settlement","sand"), registry)
            }
        }
    }
    fun unload() {
        loaded = false
        tileMap.clear()
    }
    operator fun get(location: IntVector3): TileInstance? {
        if (tileMap[location] == null) { return null }
        return TileInstance(tileMap[location]!!.identifier).at(location)
    }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE ) / V_SCALE * CELL_SIZE + offset )
    }

    private fun addColumn(location: IntVector3, height: Int, tile: Identifier, registry: ServerRegistry) {
        if (!registry.getTiles().contains(tile)) { return }
        for (i in 0 until height) {
            val instance = registry.instanceTile(tile)
            tileMap[location.copy(z = location.z + i)] = instance!!
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
