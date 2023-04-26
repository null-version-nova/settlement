package org.nullversionnova.server

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.world.WorldCell
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.tiles.TileInstance
import org.nullversionnova.server.world.Generator

class Server {
    // Members
    val loadedCells = mutableMapOf<IntVector3, WorldCell>()
    private val generator = Generator()
    val registry = ServerRegistry()

    // Methods
    fun loadPacks() {
        Engine.load(registry)
        Settlement.load(registry)
    }
    fun loadCell(location: IntVector3) {
        loadedCells[location] = WorldCell(location)
        generator.generateCell(loadedCells[location]!!,this)
    }
    fun unloadCell(location: IntVector3) { loadedCells[location]?.unload() }
    fun tick() { for (i in loadedCells.values) { i.tick(this) } }
    operator fun get(location: IntVector3) : TileInstance? { return loadedCells[location.toCell()]?.get(location.toLocal()) }
    operator fun set(location: IntVector3, tile: TileInstance) { loadedCells[location.toCell()]?.set(location.toLocal(), tile) }
}
