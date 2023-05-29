package org.nullversionnova.server

import org.nullversionnova.server.world.WorldCell
import org.nullversionnova.server.settlement.Settlement

class Server {
    // Members
    val loadedCell = WorldCell()
    val registry = ServerRegistry()
    private var tickIndex = 0

    // Methods
    fun initialize() {
        Engine.load(registry)
        Settlement.load(registry)
    }
    fun tick() { loadedCell.tick(this,tickIndex) }
}
