package org.nullversionnova

import org.nullversionnova.registry.Identifier
import org.nullversionnova.world.tiles.EngineTiles

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
