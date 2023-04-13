package org.nullversionnova.server

import org.nullversionnova.common.IntegerVector3
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.engine.entities.MobileEntity

class Server {
    // Members
    val loadedCells = mutableMapOf<IntegerVector3,WorldCell>()
    val loadedMobileEntities = mutableListOf<MobileEntity>()
    val registry = ServerRegistry()

    // Methods
    fun loadPacks() {
        Settlement.load(registry)
    }
    fun loadCell(location: IntegerVector3) {
        loadedCells[location] = WorldCell(location)
        loadedCells[location]?.generate()
    }
    fun unloadCell(location: IntegerVector3) {
        loadedCells[location]?.unload()
    }
}
