package org.nullversionnova.server.entities

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server

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
