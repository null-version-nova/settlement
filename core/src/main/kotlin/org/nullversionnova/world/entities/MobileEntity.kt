package org.nullversionnova.world.entities

import org.nullversionnova.math.Direction3
import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.Server

open class MobileEntity(override var identifier: Identifier, override var location: IntVector3) : Entity {
    // Members
    override var maxHealth: Int = 10
    override var health: Int = 10
    override var tickable: Boolean = true

    // Methods
    override fun tick(server: Server) {
        when ((0..30).random()) {
            0 -> move(Direction3.NORTH, server)
            1 -> move(Direction3.EAST, server)
            2 -> move(Direction3.SOUTH, server)
            3 -> move(Direction3.WEST, server)
            else -> {}
        }
    }
    override fun die() {}
    fun move(direction: Direction3, server: Server) {
        val newLocation = location.copy()
        newLocation[direction.axis()] += if (direction.polarity()) { 1 } else { -1 }
        if (!newLocation.outOfBounds() && !server[newLocation].isWall) {
            location = newLocation
        }
    }

    // Companions
    companion object Global {
        const val GRAVITY = 1
        const val TERMINAL_VELOCITY = -1
    }
}
