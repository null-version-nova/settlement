package org.nullversionnova.client

import kotlinx.serialization.json.Json
import org.nullversionnova.registry.MutableRegistry
import org.nullversionnova.registry.ResourceRegistry
import org.nullversionnova.registry.TextureRegistry

object ClientRegistries {
    val textureRegistry = TextureRegistry(MutableRegistry())
    val tileModelRegistry = ResourceRegistry<TileModel>(MutableRegistry(),true,"models/tiles","json") {
        Json.decodeFromString(it.readText())
    }
}
