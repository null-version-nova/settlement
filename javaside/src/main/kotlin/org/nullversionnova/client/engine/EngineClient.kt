package org.nullversionnova.client.engine

import org.nullversionnova.common.Identifier
import org.nullversionnova.client.ClientRegistry

object EngineClient {
    const val pack_identifier = "engine"
    fun loadAssets(registry: ClientRegistry) {
        registry.loadTexture(Identifier(pack_identifier,"default"))
        registry.loadTexture(Identifier(pack_identifier,"unrendered"))
        registry.loadTexture(Identifier(pack_identifier,"fog"))
    }
}
