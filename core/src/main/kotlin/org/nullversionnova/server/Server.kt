package org.nullversionnova.server

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.engine.entities.MobileEntity

class Server {
    // Members
    val loadedCells = mutableMapOf<IntVector3,WorldCell>()
    val loadedMobileEntities = mutableListOf<MobileEntity>()
    val registry = ServerRegistry()

    // Methods
    fun loadPacks() {
        Settlement.load(registry)
    }
    fun loadCell(location: IntVector3) {
        loadedCells[location] = WorldCell(location)
        loadedCells[location]?.generate()
    }
    fun unloadCell(location: IntVector3) {
        loadedCells[location]?.unload()
    }
}
