package org.nullversionnova.server.settlement

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.settlement.tiles.SettlementTiles

object Settlement {
    const val pack_identifier = "settlement"
    fun load(registry: ServerRegistry) {
        SettlementTiles.registerTiles(registry)
    }
    fun getId(input: String) : Identifier { return Identifier(pack_identifier,input) }
}
