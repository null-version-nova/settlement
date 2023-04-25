package org.nullversionnova.client.settlement

import org.nullversionnova.common.Identifier
import org.nullversionnova.client.ClientRegistry
import org.nullversionnova.server.settlement.Settlement

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
    fun getId(input: String) : Identifier { return Identifier(pack_identifier,input) }
}
