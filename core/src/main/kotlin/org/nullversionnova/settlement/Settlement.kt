package org.nullversionnova.settlement

import org.nullversionnova.registry.Identifier
import org.nullversionnova.ServerRegistry
import org.nullversionnova.settlement.world.tiles.SettlementTiles

object Settlement {
    const val PACK_IDENTIFIER = "settlement"
    fun load(registry: ServerRegistry) {
        registerProperties(registry)
        SettlementTiles.registerTiles(registry)
    }
    fun registerProperties(registry: ServerRegistry) {
        registry.addValueProperty(getId("flammability"),-1)
        registry.addValueProperty(getId("hardness"),0)
    }
    fun getId(input: String) : Identifier { return Identifier(PACK_IDENTIFIER,input) }
}
