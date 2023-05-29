package org.nullversionnova.server.world

import org.nullversionnova.SimplexNoise
import org.nullversionnova.common.IntVector2
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.tiles.TickableTile
import org.nullversionnova.server.tiles.TileInstance

class WorldCell {
    // Members
    private val tileMap = mutableMapOf<IntVector3, TileInstance>()
    private var loaded = false
    var location = IntVector3()

    // Methods
    fun generate(server: Server) {
        val heightmap = mutableMapOf<IntVector2,Int>()
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                heightmap[IntVector2(i,j)] = (
                    SimplexNoise.noise(
                        (i.toDouble() + location.x * CELL_SIZE) / Generator.H_SCALE,
                        (j.toDouble() + location.y * CELL_SIZE) / Generator.H_SCALE
                    ) / Generator.V_SCALE * CELL_SIZE).toInt() - CELL_SIZE * location.z + 128
            }
        }
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                for (k in 0 until heightmap[IntVector2(i,j)]!! - Generator.SOIL_DEPTH) {
                    try { this[IntVector3(i,j,k)] = server.registry.instanceTile("settlement:rock", IntVector3(i,j,k),server) }
                    catch (e: Exception) {
                        println(e)
                    }
                }
                for (k in heightmap[IntVector2(i,j)]!! - Generator.SOIL_DEPTH until heightmap[IntVector2(i,j)]!!) {
                    try { this[IntVector3(i,j,k)] = server.registry.instanceTile("settlement:dirt",
                        IntVector3(i,j,k),server) }
                    catch (e: Exception) {
                        println(e)
                    }
                }
                try {
                    this[IntVector3(i,j,heightmap[IntVector2(i,j)]!!)] = server.registry.instanceTile("settlement:grass",IntVector3(i,j,heightmap[IntVector2(i,j)]!!),server)
                }
                catch (e: Exception) {
                    println(e)
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
                tile.tick(i.location,server)
            }
        }
    }
    operator fun get(location: IntVector3): TileInstance? {
        return tileMap[location]
    }
    operator fun set(location: IntVector3, tile: TileInstance) { tileMap[location] = tile }

    // Companions
    companion object {
        const val CELL_SIZE = 256
    }
}
