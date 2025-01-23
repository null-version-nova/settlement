package org.nullversionnova

import org.nullversionnova.world.tiles.EngineTiles

object Engine {
    const val pack_identifier = "engine"
    fun load() {
        Registries.materialRegistry.register()
        EngineTiles
        Registries.tileRegistry.register()
    }

}
