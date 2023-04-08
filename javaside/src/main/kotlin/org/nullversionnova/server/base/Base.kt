package org.nullversionnova.server.base

import org.nullversionnova.data.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.base.tiles.BaseTiles

object Base {
    const val pack_identifier = "settlement"
    fun load(registry: ServerRegistry) {
        BaseTiles.registerTiles(registry)
    }
    fun getId(input: String) : Identifier { return Identifier(pack_identifier,input) }
}
