package org.nullversionnova.settlement.world.tiles

import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.Server
import org.nullversionnova.world.tiles.TickableTile

class Soil(material: Identifier) : TickableTile(material) {
    override fun tick(location: IntVector3, server: Server) {}
    fun growGrass(location: IntVector3, server: Server) {

    }
}
