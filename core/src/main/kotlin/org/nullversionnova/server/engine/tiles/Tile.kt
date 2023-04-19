package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry

open class Tile(var material: Identifier = Identifier()) {
    // Initialization
    constructor(material: String) : this(Identifier(material))

    // Setters
    fun material(newMaterial: Identifier) : Tile {
        material = newMaterial
        return this
    }

    // Getters
    fun checkValue(value: Identifier, registry: ServerRegistry) : Number {
        return registry.getMaterialValue(material,value)
    }
    fun checkProperty(property: Identifier, registry: ServerRegistry) : Boolean {
        return registry.isMaterial(material,property)
    }
}
