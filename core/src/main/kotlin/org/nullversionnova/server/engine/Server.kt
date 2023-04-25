package org.nullversionnova.server.engine

import org.nullversionnova.common.Global.convertPositionToCell
import org.nullversionnova.common.Global.convertPositionToLocal
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.cell.WorldCell
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.engine.entities.MobileEntity
import org.nullversionnova.server.engine.tiles.TileInstance

class Server {
    // Members
    val loadedCells = mutableMapOf<IntVector3, WorldCell>()
    val cellsToLoad = mutableListOf<IntVector3>()
    val loadedMobileEntities = mutableListOf<MobileEntity>()
    val registry = ServerRegistry()

    // Methods
    fun loadPacks() {
        Engine.load(registry)
        Settlement.load(registry)
    }
    fun loadCell(location: IntVector3) {
        loadedCells[location] = WorldCell(location)
        loadedCells[location]?.generate(registry)
        try {
            cellsToLoad.removeAt(0)
        } catch (_: Exception) {}
    }
    fun unloadCell(location: IntVector3) {
        loadedCells[location]?.unload()
    }
    fun tick() {
        if (cellsToLoad.isNotEmpty()) { loadCell(cellsToLoad.first()) }
        for (i in loadedCells.values) {
            i.tick(this)
        }
    }
    operator fun get(location: IntVector3) : TileInstance? {
        val cell = convertPositionToCell(location)
        val local = convertPositionToLocal(location)
        return loadedCells[cell]?.get(local)
    }
    operator fun set(location: IntVector3, tile: TileInstance) {
        val cell = convertPositionToCell(location)
        val local = convertPositionToLocal(location)
        loadedCells[cell]?.set(local, tile)
    }
}
