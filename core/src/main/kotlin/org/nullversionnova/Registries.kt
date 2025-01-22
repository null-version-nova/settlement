package org.nullversionnova

import kotlinx.serialization.json.Json
import org.nullversionnova.properties.InheritingPropertiesJSON
import org.nullversionnova.properties.MutableInheritingProperties
import org.nullversionnova.registry.MutableRegistry
import org.nullversionnova.registry.ResourceRegistry

object Registries {
    val materialRegistry = ResourceRegistry<MutableInheritingProperties<Int>>(MutableRegistry(),false,"materials",".json") {
        Json.decodeFromString<InheritingPropertiesJSON>(it.readText()).cast()
    }
}
