package org.nullversionnova.server.engine.entities

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.tiles.TileInstance

class StaticEntity(
    override var identifier: Identifier,
    var location: IntVector3 = IntVector3(0,0,0),
    override var maxHealth: Int = 1
) : Entity, TileInstance {
    // Members
    override var tickable = false
    override var health = maxHealth

    // Methods
    override fun tick() {}
    override fun die() {}
}
