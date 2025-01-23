package org.nullversionnova.settlement

import org.nullversionnova.ServerRegistry
import org.nullversionnova.registry.Identifier

object Settlement {
    const val PACK_IDENTIFIER = "settlement"
    fun load(registry: ServerRegistry) {
        registerProperties(registry)
    }
    fun registerProperties(registry: ServerRegistry) {
        registry.addValueProperty(getId("flammability"),-1)
        registry.addValueProperty(getId("hardness"),0)
    }
    fun getId(input: String) : Identifier { return Identifier(PACK_IDENTIFIER,input) }
}
