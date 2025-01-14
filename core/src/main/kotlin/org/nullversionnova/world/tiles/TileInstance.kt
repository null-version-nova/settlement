package org.nullversionnova.world.tiles

import org.nullversionnova.properties.InheritingProperties
import org.nullversionnova.properties.MutableMappedProperties
import org.nullversionnova.math.Direction3
import org.nullversionnova.math.IntVector3
import org.nullversionnova.registry.Identifier
import org.nullversionnova.registry.InvalidIdentifierException
import org.nullversionnova.ServerRegistry
import org.nullversionnova.world.GameObject
import org.nullversionnova.Server

class TileInstance(override var identifier: Identifier, var location : IntVector3) : MutableMappedProperties<Number>(),
    GameObject {
    // Members
    var direction : Direction3? = null
    var isFloor : Boolean = false

    // Getters
    fun getTexture() : Identifier { return identifier }
    fun getTile(registry: ServerRegistry) : Tile { return registry.accessTile(identifier) }
    fun getMaterial(registry: ServerRegistry) : InheritingProperties<Number> { return registry.getMaterial(registry.accessTile(identifier).material) }

    // Mutators
    fun increment(property: Identifier, registry: ServerRegistry, value: Number = 1) : Number {
        val currentValue = this[property] ?: return 0
        if (currentValue is Double) {
            if (registry.accessTile(identifier).maximumValues[property] != null) {
                if (currentValue.toDouble() + value.toDouble() > registry.accessTile(identifier).maximumValues[property]!!.toDouble()) {
                    return registry.accessTile(identifier).maximumValues[property]!!
                }
            }
            if (registry.accessTile(identifier).minimumValues[property] != null) {
                if (currentValue.toDouble() + value.toDouble() < registry.accessTile(identifier).minimumValues[property]!!.toDouble()) {
                    return registry.accessTile(identifier).minimumValues[property]!!
                }
            }
            this[property]!!.toDouble() + value.toDouble()
            return this[property]!!
        }
        else if (currentValue is Int) {
            if (registry.accessTile(identifier).maximumValues[property] != null) {
                if (currentValue.toInt() + value.toInt() > registry.accessTile(identifier).maximumValues[property]!!.toInt()) {
                    return registry.accessTile(identifier).maximumValues[property]!!
                }
            }
            if (registry.accessTile(identifier).minimumValues[property] != null) {
                if (currentValue.toInt() + value.toInt() < registry.accessTile(identifier).minimumValues[property]!!.toInt()) {
                    return registry.accessTile(identifier).minimumValues[property]!!
                }
            }
            this[property]!!.toInt() + value.toInt()
            return this[property]!!
        }
        return 0
    }
    fun decrement(property: Identifier, registry: ServerRegistry, value: Double = 1.0) : Number {
        return increment(property, registry, value * -1)
    }

    companion object {
        fun instanceTile(tile: Identifier, location: IntVector3, server: Server, asFloor: Boolean = false) : TileInstance {
            if (!server.registry.getTiles().contains(tile)) { throw InvalidIdentifierException()
            }
            if (location.outOfBounds()) { throw Exception("Out of bounds! Location was $location") }
            val instance = TileInstance(tile,location)
            instance.isFloor = asFloor
            server.registry.accessTile(instance.identifier).place(instance, server)
            return instance
        }
    }
}
