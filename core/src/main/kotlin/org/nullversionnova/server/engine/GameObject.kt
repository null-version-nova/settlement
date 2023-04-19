package org.nullversionnova.server.engine

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.tiles.Tile

interface GameObject {
    var identifier: Identifier
    fun getTile(registry: ServerRegistry) : Tile { return registry.accessTile(identifier) }
}
