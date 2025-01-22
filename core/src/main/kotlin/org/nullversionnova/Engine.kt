package org.nullversionnova

import org.nullversionnova.world.tiles.EngineTiles

object Engine {
    const val pack_identifier = "engine"
    fun load(registry: ServerRegistry) {
        Registries.materialRegistry.register()
        EngineTiles.registerTiles(registry)
    }

}
