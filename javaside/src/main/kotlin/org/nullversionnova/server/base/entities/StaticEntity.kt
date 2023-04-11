package org.nullversionnova.server.base.entities

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.server.base.tiles.TileInstance

class StaticEntity(
    override var identifier: Identifier,
    override var location: IntegerVector3 = IntegerVector3(0,0,0),
    override var maxHealth: Int = 1
) : Entity, TileInstance {
    // Members
    override var tickable = false
    override var health = maxHealth

    // Methods
    override fun tick() {}
    override fun die() {}
}
