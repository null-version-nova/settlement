package org.nullversionnova.client.base

import org.nullversionnova.data.Identifier
import org.nullversionnova.client.ClientRegistry
import org.nullversionnova.server.base.Base

object BaseClient {
    const val pack_identifier = Base.pack_identifier

    fun loadAssets(registry: ClientRegistry) {
        BaseTextures.loadTextures(registry)
    }
    fun getId(input: String) : Identifier { return Identifier(pack_identifier,input) }
}
