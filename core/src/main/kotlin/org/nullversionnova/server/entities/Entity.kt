package org.nullversionnova.server.entities

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.GameObject
import org.nullversionnova.server.Server

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
