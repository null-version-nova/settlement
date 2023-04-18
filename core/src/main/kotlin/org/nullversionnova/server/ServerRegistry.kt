package org.nullversionnova.server

import com.badlogic.gdx.Gdx
import com.beust.klaxon.Klaxon
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.Properties
import org.nullversionnova.server.engine.Material
import org.nullversionnova.server.engine.tiles.EngineTiles.NULL_TILE
import org.nullversionnova.server.engine.tiles.TileProperties

class ServerRegistry {
    // Members
    private val tiles = mutableMapOf<Identifier, TileProperties>()
    private val valueProperties = mutableMapOf<Identifier,Number>()
    private val properties = mutableSetOf<Identifier>()
    private val materials = mutableMapOf<Identifier,Material>()

    // Loading
    fun addTile(identifier: Identifier, properties: TileProperties) : TileProperties {
        return if (tiles[identifier] == null) {
            tiles[identifier] = properties
            properties
        } else {
            tiles[identifier]!!
        }
    }
    fun addValueProperty(identifier: Identifier, default: Number) { valueProperties[identifier] = default }
    fun addMaterial(identifier: Identifier) {
        val material = Material()
        val data = try {
            Klaxon().parse<Properties>(Gdx.files.internal("server/${identifier.pack}/materials/${identifier.name}.json").readString())
        } catch (e: Exception) {
            println("Warning: Exception while loading JSON associated with identifier $identifier")
            println(e)
            null
        }
        if (data != null) {
            if (identifier != Identifier()) {
                if (data.parent == null) { material.parent = Identifier() }
                else if (materials.contains(Identifier(data.parent))) { material.parent = Identifier(data.parent) }
                else { println("Error: Invalid parent") }
            }
            for (i in data.values) {
                if (valueProperties.contains(Identifier(i.property))) { material.valueProperties[Identifier(i.property)] = i.value }
                else { println("Error: Invalid property") }
            }
            for (i in data.propertyAffirmation) {
                material.propertyAffirmation.add(i)
                properties.add(i)
            }
        } else {
            if (identifier != Identifier()) { material.parent = Identifier() }
        }
        materials[identifier] = material
    }

    // Retrieving
    fun accessTile(tile: Identifier) : TileProperties {
        if (tiles[tile] == null) { tiles[tile] = NULL_TILE }
        return tiles[tile]!!
    }
    fun getDefaultValue(property: Identifier) : Number { return getMaterialValueProperty(Identifier(),property) }
    fun getMaterialValueProperty(material: Identifier, property: Identifier) : Number {
        return if (!materials.contains(material) || !valueProperties.contains(property)) {
            println("Error: Invalid identifier")
            0
        } else if (materials[material]!!.valueProperties[property] != null) {
            materials[material]!!.valueProperties[property]!!
        } else if (material == Identifier()) { valueProperties[property]!! }
        else { getMaterialValueProperty(materials[material]!!.parent!!, property) }
    }
    fun isMaterial(material: Identifier, property: Identifier) : Boolean {
        return if (materials.contains(material) && properties.contains(property)) {
            if (materials[material]!!.propertyAffirmation.contains(property)) {
                true
            } else if (materials[material]!!.propertyNegation.contains(property) || material == Identifier()) {
                false
            } else {
                isMaterial(materials[material]!!.parent!!,property)
            }
        } else {
            println("Invalid identifier")
            false
        }
    }

    // Getting
    fun getTiles() : Set<Identifier> { return tiles.keys }
    fun getValueProperties() : Set<Identifier> { return valueProperties.keys }
    fun getProperties() : Set<Identifier> { return properties }
    fun getMaterials() : Set<Identifier> { return materials.keys }

    // Alternative Calls
    fun addTile(pack: String, name: String, properties: TileProperties) : TileProperties { return addTile(Identifier(pack,name),properties) }
    fun addValueProperty(pack: String, name: String, default: Number) { addValueProperty(Identifier(pack,name),default) }
    fun addMaterial(pack: String, name: String) { addMaterial(Identifier(pack,name)) }
    fun accessTile(pack: String, name: String) : TileProperties { return accessTile(Identifier(pack,name)) }
}
