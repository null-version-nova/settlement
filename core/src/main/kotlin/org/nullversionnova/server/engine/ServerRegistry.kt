package org.nullversionnova.server.engine

import com.badlogic.gdx.Gdx
import com.beust.klaxon.Klaxon
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.common.properties.MutableInheritingProperties
import org.nullversionnova.common.properties.InheritingPropertiesJSON
import org.nullversionnova.common.InvalidIdentifierException
import org.nullversionnova.common.properties.InheritingProperties
import org.nullversionnova.server.engine.tiles.EngineTiles.NULL_TILE
import org.nullversionnova.server.engine.tiles.Tile
import org.nullversionnova.server.engine.tiles.TileInstance

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
    fun instanceTile(tile: Identifier, location: IntVector3) : TileInstance? {
        if (!tiles.containsKey(tile)) { return null }
        val instance = TileInstance(tile,location)
        for (i in tiles[tile]!!.defaultValues.keys) {
            instance[i] = tiles[tile]!!.defaultValues[i]!!
        }
        return instance
    }

    // Getting
    fun getTiles() : Set<Identifier> { return tiles.keys }
    fun getValueProperties() : Set<Identifier> { return valueProperties.keys }
    fun getProperties() : Set<Identifier> { return properties }
    fun getMaterials() : Set<Identifier> { return materials.keys }

    // Alternative Calls
    fun addTile(pack: String, name: String, properties: Tile) : Tile { return addTile(Identifier(pack,name),properties) }
    fun addValueProperty(pack: String, name: String, default: Number) { addValueProperty(Identifier(pack,name),default) }
    fun addMaterial(pack: String, name: String) { addMaterial(Identifier(pack,name)) }
    fun accessTile(pack: String, name: String) : Tile { return accessTile(Identifier(pack,name)) }
    fun getMaterial(material: String) : InheritingProperties<Number> { return getMaterial(Identifier(material)) }
}
