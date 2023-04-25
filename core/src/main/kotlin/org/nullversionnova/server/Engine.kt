package org.nullversionnova.server

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.tiles.EngineTiles

object Engine {
    const val pack_identifier = "engine"
    fun load(registry: ServerRegistry) {
        loadMaterials(registry)
        EngineTiles.registerTiles(registry)
    }
    fun loadMaterials(registry: ServerRegistry) {
        registry.addMaterial(Identifier())
    }
}
