package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Server

abstract class TickableTile(material: Identifier) : Tile(material) {
    constructor(material: String) : this(Identifier(material))

    abstract fun tick(location: IntVector3, server: Server)
}
