package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.engine.ServerRegistry
import org.nullversionnova.server.engine.GameObject

open class Tile(var material: Identifier = Identifier(), override var identifier: Identifier = Identifier()) : GameObject {
    // Initialization
    constructor(material: String) : this(Identifier(material))

    // Members
    open val defaultValues = mutableMapOf<Identifier,Number>(
        // Pair(property, default), Pair(otherProperty, otherDefault)
    )
    open val minimumValues = mutableMapOf<Identifier,Number>(
        // Pair(property, minimum), Pair(otherProperty, otherMinimum)
    )
    open val maximumValues = mutableMapOf<Identifier,Number>(
        // Pair(property, Maximum), Pair(otherProperty, otherMaximum)
    )

    // Setters
    fun material(newMaterial: Identifier) : Tile {
        material = newMaterial
        return this
    }
    fun addProperty(property: Identifier, default: Number, max: Number? = null, min: Number? = null) : Tile {
        defaultValues[property] = default
        if (max != null) { maximumValues[property] = max }
        if (min != null) { minimumValues[property] = min }
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