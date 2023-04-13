package org.nullversionnova.server.engine.entities

import org.nullversionnova.common.Identifier

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
