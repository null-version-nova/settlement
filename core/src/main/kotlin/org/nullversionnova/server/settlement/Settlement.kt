package org.nullversionnova.server.settlement

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.settlement.tiles.SettlementTiles

object Settlement {
    const val pack_identifier = "settlement"
    fun load(registry: ServerRegistry) {
        registerProperties(registry)
        registerMaterials(registry)
        SettlementTiles.registerTiles(registry)
    }
    fun registerProperties(registry: ServerRegistry) {
        registry.addValueProperty(getId("flammability"),-1)
        registry.addValueProperty(getId("hardness"),0)
    }
    fun registerMaterials(registry: ServerRegistry) {
        registry.addMaterial(getId("rock"))
        registry.addMaterial(getId("soil"))
        registry.addMaterial(getId("organic"))
        registry.addMaterial(getId("plant"))
        registry.addMaterial(getId("wood"))
    }
    fun getId(input: String) : Identifier { return Identifier(pack_identifier,input) }
}
