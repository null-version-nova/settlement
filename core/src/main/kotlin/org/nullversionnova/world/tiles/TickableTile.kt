package org.nullversionnova.world.tiles

import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.Server

abstract class TickableTile(material: Identifier = Identifier()) : Tile(material) {
    constructor(material: String) : this(Identifier(material))

    abstract fun tick(location: IntVector3, server: Server)
}
