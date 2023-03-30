package org.nullversionnova.client.base

import org.nullversionnova.client.ClientRegistry
import org.nullversionnova.client.base.BaseClient.getId

object BaseTextures {
    fun loadTextures(registry: ClientRegistry) {
        registry.loadTextureFromTile(getId("rock"))
    }
}