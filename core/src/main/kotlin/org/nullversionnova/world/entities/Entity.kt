package org.nullversionnova.world.entities

import org.nullversionnova.Server
import org.nullversionnova.math.IntVector3
import org.nullversionnova.world.RegistryObject

interface Entity : RegistryObject {
    // Members
    var tickable : Boolean
    var maxHealth : Int
    var health : Int
    val location : IntVector3

    // Methods
    fun tick(server: Server)
    fun die()
}
