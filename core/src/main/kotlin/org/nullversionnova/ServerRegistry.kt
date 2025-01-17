package org.nullversionnova

import com.badlogic.gdx.Gdx
import com.beust.klaxon.Klaxon
import org.nullversionnova.registry.Identifier
import org.nullversionnova.properties.MutableInheritingProperties
import org.nullversionnova.properties.InheritingPropertiesJSON
import org.nullversionnova.registry.InvalidIdentifierException
import org.nullversionnova.properties.InheritingProperties
import org.nullversionnova.world.tiles.EngineTiles.NULL_TILE
import org.nullversionnova.world.tiles.Tile

class ServerRegistry {
    // Members
    private val tiles = mutableMapOf<Identifier, Tile>()
    private val valueProperties = mutableMapOf<Identifier,Number>()
    private val properties = mutableSetOf<Identifier>()
    private val materials = mutableMapOf<Identifier, MutableInheritingProperties<Number>>()

    // Loading
    fun addTile(identifier: Identifier, properties: Tile) : Tile {
        return if (tiles[identifier] == null) {
            tiles[identifier] = properties
            tiles[identifier]!!.identifier = identifier
            tiles[identifier]!!
        } else {
            tiles[identifier]!!
        }
    }
    fun addValueProperty(identifier: Identifier, default: Number) { valueProperties[identifier] = default }
    fun addMaterial(identifier: Identifier) {
        val material = try {
            Klaxon().parse<InheritingPropertiesJSON>(Gdx.files.internal("server/${identifier.pack}/materials/${identifier.name}.json").readString())?.cast(this,identifier)
        } catch (e: Exception) {
            println("Warning: Exception while loading JSON associated with identifier $identifier")
            println(e)
            return
        }
        materials[identifier] = material!!
    }

    // Retrieving
    fun accessTile(tile: Identifier) : Tile {
        if (tiles[tile] == null) { tiles[tile] = NULL_TILE }
        return tiles[tile]!!
    }
    fun getDefaultValue(property: Identifier) : Number { return getMaterialValue(Identifier(),property) }
    fun getMaterialValue(material: Identifier, property: Identifier) : Number {
        return if (!materials.contains(material) || !valueProperties.contains(property)) {
            throw InvalidIdentifierException()
        }
        else { materials[material]!![property]!! }
    }
    fun isMaterial(material: Identifier, property: Identifier) : Boolean {
        return if (materials.contains(material) && properties.contains(property)) {
            materials[material]!!.hasProperty(property)
        } else { throw InvalidIdentifierException() }
    }
    fun getMaterial(material: Identifier? = null) : InheritingProperties<Number> {
        return if (material == null) {
            val item = MutableInheritingProperties<Number>()
            for (i in valueProperties.keys) {
                item[i] = valueProperties[i]!!
            }
            item.staticCopy()
        } else {
            materials[material]!!.staticCopy()
        }
    }

    // Getting
    fun getTiles() : Set<Identifier> { return tiles.keys }
    fun getValueProperties() : Set<Identifier> { return valueProperties.keys }
    fun getProperties() : Set<Identifier> { return properties }
    fun getMaterials() : Set<Identifier> { return materials.keys }

    // Alternative Calls
    fun addTile(pack: String, name: String, properties: Tile) : Tile { return addTile(Identifier(pack,name),properties) }
    fun addTile(identifier: String, properties: Tile) : Tile { return addTile(Identifier(identifier),properties) }
    fun addValueProperty(pack: String, name: String, default: Number) { addValueProperty(Identifier(pack,name),default) }
    fun addMaterial(pack: String, name: String) { addMaterial(Identifier(pack,name)) }
    fun accessTile(pack: String, name: String) : Tile { return accessTile(Identifier(pack,name)) }
    fun getMaterial(material: String) : InheritingProperties<Number> { return getMaterial(Identifier(material)) }
}
