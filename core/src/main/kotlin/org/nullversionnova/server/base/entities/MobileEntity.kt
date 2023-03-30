package org.nullversionnova.server.base.entities

import org.nullversionnova.Identifier
import com.badlogic.gdx.math.Vector3

class MobileEntity(identifier: Identifier, var position: Vector3) : Entity(identifier) {

    // Constructors
    init {
        tickable = true
    }

    // Members
    var velocity: Vector3 = Vector3(0f,0f,0f)
    var adjacentSurfaces = BooleanArray(6)
    var isGravityAffected = true

    // Methods
    override fun tick() {
        // Adjacency Checks
        if ((velocity.x > 0 && adjacentSurfaces[0]) || (velocity.x < 0 && adjacentSurfaces[1])) {velocity.x = 0f}
        if ((velocity.y > 0 && adjacentSurfaces[2]) || (velocity.y < 0 && adjacentSurfaces[3])) {velocity.y = 0f}
        if ((velocity.z > 0 && adjacentSurfaces[4]) || (velocity.z < 0 && adjacentSurfaces[5])) {velocity.z = 0f}

        // Movement
        position.x += velocity.x
        position.y += velocity.y
        position.z += velocity.z

        // Gravity
        if (!adjacentSurfaces[5] && velocity.z >= TERMINAL_VELOCITY && isGravityAffected) {velocity.z -= GRAVITY
        }
    }
    override fun die() {
        TODO("Not yet implemented")
    }

    // Companions
    companion object Global {
        const val GRAVITY = 1
        const val TERMINAL_VELOCITY = -1
    }
}