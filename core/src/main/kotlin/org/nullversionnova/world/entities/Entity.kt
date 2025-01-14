package org.nullversionnova.world.entities

import org.nullversionnova.math.IntVector3
import org.nullversionnova.world.GameObject
import org.nullversionnova.Server

interface Entity : GameObject {
    // Members
    var tickable : Boolean
    var maxHealth : Int
    var health : Int
    val location : IntVector3

    // Methods
    fun tick(server: Server)
    fun die()
}
