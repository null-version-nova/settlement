package org.nullversionnova.client.core

import org.nullversionnova.data.Identifier
import org.nullversionnova.client.ClientRegistry

object CoreClient {
    const val pack_identifier = "core"
    fun loadAssets(registry: ClientRegistry) {
        registry.loadTexture(Identifier(pack_identifier,"default"))
        registry.loadTexture(Identifier(pack_identifier,"unrendered"))
    }
}
