package org.nullversionnova.world.tiles

import org.nullversionnova.Server
import org.nullversionnova.math.IntVector3
import org.nullversionnova.properties.InheritingProperties

abstract class TickableTile(material: InheritingProperties<Int>) : Tile(material) {
    abstract fun tick(location: IntVector3, server: Server)
}
