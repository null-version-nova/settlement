package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.*
import org.nullversionnova.common.properties.InheritingProperties
import org.nullversionnova.common.properties.MutableMappedProperties
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.GameObject

class TileInstance(override var identifier: Identifier, var location : IntVector3? = null) : MutableMappedProperties<Number>(), GameObject {
    // Members
    var direction : Direction? = null

    // Methods
    fun getTile(registry: ServerRegistry) : Tile { return registry.accessTile(identifier) }
    fun getMaterial(registry: ServerRegistry) : InheritingProperties<Number> { return registry.getMaterial(registry.accessTile(identifier).material) }
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
    fun decrement(property: Identifier, registry: ServerRegistry, value: Int = 1) : Number {
        return increment(property, registry, value * -1)
    } // Interface types are sometimes terrible
}
