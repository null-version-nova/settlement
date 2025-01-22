package org.nullversionnova.world.tiles

import org.nullversionnova.Registries
import org.nullversionnova.properties.InheritingProperties
import org.nullversionnova.properties.MutableMappedProperties
import org.nullversionnova.math.Direction3
import org.nullversionnova.math.IntVector3
import org.nullversionnova.registry.Identifier
import org.nullversionnova.Server

class TileInstance(var tileType: Tile, var location : IntVector3) : MutableMappedProperties<Number>() {
    // Members
    var direction : Direction3? = null
    var isFloor : Boolean = false

    // Getters
    fun getTexture() : Identifier { return tileType.identifier }
    fun getMaterial() : InheritingProperties<Int> { return Registries.materialRegistry[tileType.material]!! }

    // Mutators
    fun increment(property: Identifier, value: Number = 1) : Number {
        val currentValue = this[property] ?: return 0
        if (currentValue is Double) {
            if (tileType.maximumValues[property] != null) {
                if (currentValue.toDouble() + value.toDouble() > tileType.maximumValues[property]!!.toDouble()) {
                    return tileType.maximumValues[property]!!
                }
            }
            if (tileType.minimumValues[property] != null) {
                if (currentValue.toDouble() + value.toDouble() < tileType.minimumValues[property]!!.toDouble()) {
                    return tileType.minimumValues[property]!!
                }
            }
            this[property]!!.toDouble() + value.toDouble()
            return this[property]!!
        }
        else if (currentValue is Int) {
            if (tileType.maximumValues[property] != null) {
                if (currentValue.toInt() + value.toInt() > tileType.maximumValues[property]!!.toInt()) {
                    return tileType.maximumValues[property]!!
                }
            }
            if (tileType.minimumValues[property] != null) {
                if (currentValue.toInt() + value.toInt() < tileType.minimumValues[property]!!.toInt()) {
                    return tileType.minimumValues[property]!!
                }
            }
            this[property]!!.toInt() + value.toInt()
            return this[property]!!
        }
        return 0
    }
    fun decrement(property: Identifier, value: Double = 1.0) : Number {
        return increment(property, value * -1)
    }

    companion object {
        fun instanceTile(tile: Tile, location: IntVector3, server: Server, asFloor: Boolean = false) : TileInstance {
//            if (!server.registry.getTiles().contains(tile)) throw InvalidIdentifierException()
            if (location.outOfBounds()) { throw Exception("Out of bounds! Location was $location") }
            val instance = TileInstance(tile,location)
            instance.isFloor = asFloor
            tile.place(instance, server)
            return instance
        }
    }
}
