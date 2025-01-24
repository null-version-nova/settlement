package org.nullversionnova.world.tiles

import org.nullversionnova.Registries
import org.nullversionnova.Server
import org.nullversionnova.client.ClientRegistries
import org.nullversionnova.properties.InheritingProperties
import org.nullversionnova.registry.Identifier
import org.nullversionnova.world.AbstractRegistryObject

open class Tile(var material: InheritingProperties<Int>? = null) : AbstractRegistryObject() {
    // Members
    open val defaultValues = mutableMapOf<Identifier,Number>(
        // Pair(property, default), Pair(otherProperty, otherDefault)...
    )
    open val minimumValues = mutableMapOf<Identifier,Number>(
        // Pair(property, minimum), Pair(otherProperty, otherMinimum)...
    )
    open val maximumValues = mutableMapOf<Identifier,Number>(
        // Pair(property, Maximum), Pair(otherProperty, otherMaximum)...
    )
    private val properties = mutableSetOf<Identifier>()

    val textureModel by lazy { ClientRegistries.textureRegistry[identifier] }

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
    fun checkMaterialValue(value: Identifier) : Number {
        return material?.get(value) ?: Registries.valuePropertyRegistry[identifier] ?: 0
    }
    fun checkMaterialProperty(property: Identifier) : Boolean {
        return material?.hasProperty(property) == true
    }
    fun checkTileProperty(property: Identifier) : Boolean {
        return properties.contains(property)
    }
}
