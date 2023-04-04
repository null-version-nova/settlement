package org.nullversionnova.server

import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.server.base.Base
import org.nullversionnova.server.base.entities.MobileEntity

class Server {
    // Members
    val loadedCells = mutableMapOf<IntegerVector3,WorldCell>()
    val loadedMobileEntities = mutableListOf<MobileEntity>()
    val registry = ServerRegistry()

    // Methods
    fun loadPacks() {
        Base.load(registry)
    }
    fun loadCell(location: IntegerVector3) {
        loadedCells[location] = WorldCell()
    }
}
