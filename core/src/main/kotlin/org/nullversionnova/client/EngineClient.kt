package org.nullversionnova.client

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.Engine

object EngineClient {
    const val pack_identifier = Engine.pack_identifier
    fun loadAssets(registry: ClientRegistry) {
        registry.loadTexture(Identifier())
        registry.loadTexture(Identifier(pack_identifier,"fog"))
    }
}
