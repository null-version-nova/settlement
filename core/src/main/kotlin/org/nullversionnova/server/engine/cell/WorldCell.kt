package org.nullversionnova.server.engine.cell

import org.nullversionnova.SimplexNoise
import org.nullversionnova.common.Global.convertPositionToGlobal
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Server
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
                if (isDesert(i,j)) {
                    addColumn(IntVector3(i,j,height), SOIL_DEPTH, Identifier("settlement","sand"), registry)
                } else {
                    addColumn(IntVector3(i,j,height), SOIL_DEPTH, Identifier("settlement","dirt"), registry)
                }
            }
        }
    }
    fun unload() {
        loaded = false
        tileMap.clear()
    }
    fun tick(server: Server) {
        for (i in tileMap.values) {
            val tile = server.registry.accessTile(i.identifier)
            if (tile is TickableTile) {
                tile.tick(convertPositionToGlobal(location,i.location!!),server)
            }
        }
    }
    operator fun get(location: IntVector3): TileInstance? {
        if (tileMap[location] == null) { return null }
        val tile = tileMap[location]!!
        tile.location = location
        return tile
    }
    fun getOrigin(location: IntVector3) : TileInstance? {
        return tileMap[location]
    }
    operator fun set(location: IntVector3, tile: TileInstance) { tileMap[location] = tile }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE ) / V_SCALE * CELL_SIZE + offset )
    }
    private fun isDesert(xin : Number, yin : Number) : Boolean {
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE_DESERT, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE_DESERT ) / V_SCALE_DESERT * CELL_SIZE  > 0)
    }

    private fun addColumn(location: IntVector3, height: Int, tile: Identifier, registry: ServerRegistry) {
        if (!registry.getTiles().contains(tile)) { return }
        for (i in 0 until height) {
            val instance = registry.instanceTile(tile)
            tileMap[location.copy(z = location.z + i)] = instance!!.at(location)
        }
    }

    // Companions
    companion object Global {
        const val CELL_SIZE = 32
        const val H_SCALE : Double = 50.0
        const val V_SCALE : Double = 5.0
        const val H_SCALE_DESERT : Double = 100.0
        const val V_SCALE_DESERT : Double = 2.0
        const val Y_OFFSET : Int = 50
        const val SOIL_DEPTH : Int = 3
    }
}
