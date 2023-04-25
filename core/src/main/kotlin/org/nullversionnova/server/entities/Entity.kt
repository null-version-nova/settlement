package org.nullversionnova.server.entities

import org.nullversionnova.common.Identifier
import org.nullversionnova.server.GameObject

interface Entity : GameObject {
    // Members
    override var identifier: Identifier
    var tickable: Boolean
    var maxHealth : Int
    var health : Int

    // Methods
    fun tick()
    fun die()
}
