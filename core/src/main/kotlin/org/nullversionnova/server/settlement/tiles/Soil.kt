package org.nullversionnova.server.settlement.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Server
import org.nullversionnova.server.engine.tiles.TickableTile

class Soil(material: Identifier) : TickableTile(material) {
    override fun tick(location: IntVector3, server: Server) {
        val tile = server[location]?.identifier?.let { server.registry.accessTile(it) }
        if (tile is Soil) {
            tile.growGrass(location,server)
        }
    }
    fun growGrass(location: IntVector3, server: Server) {
        val newTile = server[location]
        newTile?.make("settlement:grass")
        newTile?.setTexture("settlement:grass")
        if (newTile != null) {
            server[location] = newTile
        }
    }
}