package org.nullversionnova.world.tiles

import org.nullversionnova.Registries
import org.nullversionnova.registry.Identifier
import org.nullversionnova.Server
import org.nullversionnova.world.GameObject

open class Tile(var material: Identifier = Identifier(), override var identifier: Identifier = Identifier()) :
    GameObject {
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
    private val properties = mutableSetOf<Identifier>()

    // Methods
    open fun place(tile: TileInstance, server: Server) {
        for (i in defaultValues.keys) {
            tile[i] = defaultValues[i]!!
        }
        if (checkTileProperty(Identifier("engine","always_floor"))) {
            tile.isFloor = true
        }
    }

    // Setters
    fun material(newMaterial: Identifier) : Tile {
        material = newMaterial
        return this
    }
    fun addProperty(property: Identifier) : Tile {
        properties.add(property)
        return this
    }
    fun removeProperty(property: Identifier) : Tile {
        properties.remove(property)
        return this
    }
    fun addValueProperty(property: Identifier, default: Number, max: Number? = null, min: Number? = null) : Tile {
        defaultValues[property] = default
        if (max != null) { maximumValues[property] = max }
        if (min != null) { minimumValues[property] = min }
        return this
    }

    // Getters
    fun checkValue(value: Identifier) : Number {
        return Registries.materialRegistry[material]?.get(value)!!
    }
    fun checkMaterialProperty(property: Identifier) : Boolean {
        return Registries.materialRegistry[material]!!.hasProperty(property)
    }
    fun checkTileProperty(property: Identifier) : Boolean {
        return properties.contains(property)
    }
}
