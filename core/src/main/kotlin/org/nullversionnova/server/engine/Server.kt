package org.nullversionnova.server.engine

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.cell.WorldCell
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.engine.entities.MobileEntity

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
//        loadedCells[location]?.optimize(registry)
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
            for (j in i.tileMap.values) {
//                j.tick(this)
            }
        }
    }
}
