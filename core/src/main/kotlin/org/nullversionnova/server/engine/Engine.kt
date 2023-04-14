package org.nullversionnova.server.engine

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.tiles.EngineTiles

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
