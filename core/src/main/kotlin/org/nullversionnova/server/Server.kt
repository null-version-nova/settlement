package org.nullversionnova.server

import org.nullversionnova.server.world.WorldCell
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.world.Generator

class Server {
    // Members
    val loadedCell = WorldCell()
    private val generator = Generator()
    val registry = ServerRegistry()

    // Methods
    fun initialize() {
        Engine.load(registry)
        Settlement.load(registry)
    }
    fun tick() { loadedCell.tick(this) }
}
