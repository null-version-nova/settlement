package org.nullversionnova.client.core

import org.nullversionnova.data.Identifier
import org.nullversionnova.client.ClientRegistry

object SystemClient {
    const val pack_identifier = "system"
    fun loadAssets(registry: ClientRegistry) {
        registry.loadTexture(Identifier(pack_identifier,"default"))
        registry.loadTexture(Identifier(pack_identifier,"unrendered"))
        registry.loadTexture(Identifier(pack_identifier,"fog"))
    }
}
