package org.nullversionnova.settlement.client

import org.nullversionnova.client.ClientRegistry
import org.nullversionnova.registry.Identifier
import org.nullversionnova.settlement.Settlement

object SettlementClient {
    const val pack_identifier = Settlement.pack_identifier

    fun loadAssets(registry: ClientRegistry) {
        loadTextures(registry)
    }
    fun loadTextures(registry: ClientRegistry) {
        registry.loadTexture(getId("rock"))
        registry.loadTexture(getId("dirt"))
        registry.loadTexture(getId("sand"))
        registry.loadTexture(getId("grass_side"))
        registry.loadTexture(getId("grass_top"))
    }
    fun getId(input: String) : Identifier { return Identifier(pack_identifier, input)
    }
}
