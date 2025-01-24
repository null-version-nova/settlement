package org.nullversionnova.settlement

import org.nullversionnova.Registries

object Settlement {
    const val PACK_IDENTIFIER = "settlement"
    fun load() {
        registerProperties()
    }
    fun registerProperties() {
        Registries.valuePropertyRegistry.register("settlement:flammability",-1)
        Registries.valuePropertyRegistry.register("settlement:hardness",0)
    }
}
