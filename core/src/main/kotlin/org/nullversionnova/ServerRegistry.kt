package org.nullversionnova

import org.nullversionnova.registry.Identifier
import org.nullversionnova.world.tiles.EngineTiles.NULL_TILE
import org.nullversionnova.world.tiles.Tile

class ServerRegistry {
    // Members
    private val tiles = mutableMapOf<Identifier, Tile>()
    private val valueProperties = mutableMapOf<Identifier,Number>()

    fun addValueProperty(identifier: Identifier, default: Number) { valueProperties[identifier] = default }

    // Retrieving
    fun accessTile(tile: Identifier) : Tile {
        if (tiles[tile] == null) { tiles[tile] = NULL_TILE }
        return tiles[tile]!!
    }
}
