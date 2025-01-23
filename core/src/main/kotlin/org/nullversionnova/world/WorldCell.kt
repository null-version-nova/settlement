package org.nullversionnova.world

import org.nullversionnova.SimplexNoise
import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector2
import org.nullversionnova.math.IntVector3
import org.nullversionnova.registry.InvalidIdentifierException
import org.nullversionnova.Server
import org.nullversionnova.settlement.world.tiles.SettlementTiles
import org.nullversionnova.world.tiles.TickableTile
import org.nullversionnova.world.tiles.Tile
import org.nullversionnova.world.tiles.TileInstance
import org.nullversionnova.world.tiles.TileInstance.Companion.instanceTile

class WorldCell {
    // Members
    private val tileMap = mutableMapOf<IntVector3, TileInstance>()
    private val tickableSet = mutableListOf<IntVector3>()
    var location = IntVector3()
    var loaded = false

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
                    placeTile(SettlementTiles.ROCK,IntVector3(i,j,k),server)
                }
                for (k in heightmap[IntVector2(i,j)]!! - Generator.SOIL_DEPTH until heightmap[IntVector2(i,j)]!!) {
                    placeTile(SettlementTiles.SAND,IntVector3(i,j,k),server)
                }
            }
        }
        loaded = true
    }
    fun placeTile(tile: Tile, location: IntVector3, server: Server) {
        try {
            tileMap[location] = instanceTile(tile,location,server)
            if (tile is TickableTile) {
                tickableSet.add(location)
            }
        } catch (e: InvalidIdentifierException) {
            println("Error: Tried to place ${tile.identifier} at $location, which does not exist")
        } catch (e: Exception) {
            println("Error: Tried to place ${tile.identifier} at $location, which is out of bounds")
        }
    }
    fun tick(server: Server,tickIndex: Int) {
        if (tickableSet.isEmpty()) return
        location = tickableSet[tickIndex]
        val tile = tileMap[location]!!.tileType
        if (tile is TickableTile) {
            tile.tick(location,server)
        }
    }
    operator fun get(location: IntVector3): TileInstance? {
        return tileMap[location]
    }

    // Companions
    companion object {
        const val CELL_SIZE = 256
    }
}
