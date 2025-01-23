package org.nullversionnova

import kotlinx.serialization.json.Json
import org.nullversionnova.properties.InheritingPropertiesJSON
import org.nullversionnova.properties.MutableInheritingProperties
import org.nullversionnova.registry.ConstructorRegistry
import org.nullversionnova.registry.MutableRegistry
import org.nullversionnova.registry.ResourceRegistry
import org.nullversionnova.world.tiles.Tile

object Registries {
    val materialRegistry = ResourceRegistry<MutableInheritingProperties<Int>>(MutableRegistry<MutableInheritingProperties<Int>>(),false,"materials",".json") {
        Json.decodeFromString<InheritingPropertiesJSON>(it.readText()).cast()
    }
    val tileRegistry = ConstructorRegistry<Tile>(MutableRegistry())
}
