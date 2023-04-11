package org.nullversionnova.server.base.entities

import org.nullversionnova.data.Identifier

interface Entity {
    // Members
    var identifier: Identifier
    var tickable: Boolean
    var maxHealth : Int
    var health : Int

    // Methods
    fun tick()
    fun die()
}
