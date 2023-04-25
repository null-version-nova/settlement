package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.tiles.TickableTile

class Soil(material: Identifier) : TickableTile(material) {
    override fun tick(location: IntVector3, server: Server) {}
    fun growGrass(location: IntVector3, server: Server) {
        if (server[location]?.hasProperty("settlement:grass") == false) {
            server[location]!!.make("settlement:grass")
            server[location]!!.setTexture("settlement:grass")
        }
    }
}
