package org.nullversionnova.server.engine

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.tiles.TileProperties

interface GameObject {
    var identifier: Identifier
    fun getTile(registry: ServerRegistry) : TileProperties { return registry.accessTile(identifier) }
}
