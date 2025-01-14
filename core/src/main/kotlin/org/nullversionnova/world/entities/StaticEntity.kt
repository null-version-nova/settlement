package org.nullversionnova.world.entities

import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.Server

class StaticEntity(
    override var identifier: Identifier,
    override var location: IntVector3 = IntVector3(0,0,0),
    override var maxHealth: Int = 1
) : Entity {
    // Members
    override var tickable = false
    override var health = maxHealth

    // Methods
    override fun tick(server: Server) {}
    override fun die() {}
}
