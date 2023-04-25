package org.nullversionnova.server.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server

abstract class TickableTile(material: Identifier = Identifier()) : Tile(material) {
    constructor(material: String) : this(Identifier(material))

    abstract fun tick(location: IntVector3, server: Server)
}
