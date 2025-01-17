package org.nullversionnova.settlement

import kotlinx.serialization.json.Json
import org.nullversionnova.properties.InheritingPropertiesJSON
import org.nullversionnova.properties.MutableInheritingProperties
import org.nullversionnova.registry.MutableRegistry
import org.nullversionnova.registry.ResourceRegistry

object SettlementRegistries {
    val MaterialRegistry = ResourceRegistry<MutableInheritingProperties<Number>>(MutableRegistry(),false,"materials",".json") {
        Json.decodeFromString<InheritingPropertiesJSON>(it.readText()).cast()
    }
}
