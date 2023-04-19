package org.nullversionnova.server

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Engine
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.engine.entities.MobileEntity

class Server {
    // Members
    val loadedCells = mutableMapOf<IntVector3,WorldCell>()
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
    }
    fun unloadCell(location: IntVector3) {
        loadedCells[location]?.unload()
    }
    fun tick() {
        for (i in loadedCells.values) {
            for (j in i.tickableTileMap) {
                j.tick(this)
            }
        }
    }
}
