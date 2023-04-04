package org.nullversionnova.server

import org.nullversionnova.data.Identifier
import tiles.Tile

class ServerRegistry {
    // Members
    private val tileSet = mutableMapOf<Identifier, Tile>()

    // Loading
    fun addTile(identifier: Identifier, properties: Tile) : Tile {
        return if (tileSet[identifier] == null) {
            tileSet[identifier] = properties
            properties
        } else {
            tileSet[identifier]!!
        }
    }

    // Retrieving
    fun accessTile(identifier: Identifier) : Tile {
        if (tileSet[identifier] == null) {
            tileSet[identifier] = Tile("default")
        }
        return tileSet[identifier]!!
    }

    // Getting
    fun getTileSet() : Set<Identifier> {
        return tileSet.keys
    }
}
