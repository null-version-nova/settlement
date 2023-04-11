package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import org.nullversionnova.server.base.tiles.TileProperties

class ServerRegistry {
    // Members
    private val tileSet = mutableMapOf<Identifier, TileProperties>()

    // Loading
    fun addTile(identifier: Identifier, properties: TileProperties) : TileProperties {
        return if (tileSet[identifier] == null) {
            tileSet[identifier] = properties
            properties
        } else {
            tileSet[identifier]!!
        }
    }

    // Retrieving
    fun accessTile(identifier: Identifier) : TileProperties {
        if (tileSet[identifier] == null) {
            tileSet[identifier] = TileProperties("default")
        }
        return tileSet[identifier]!!
    }

    // Getting
    fun getTileSet() : Set<Identifier> {
        return tileSet.keys
    }
}
