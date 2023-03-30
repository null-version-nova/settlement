package org.nullversionnova.server.base.entities

import org.nullversionnova.Identifier

abstract class Entity(val identifier: Identifier) {
    // Members
    var tickable = false
    var maxHealth = 1
    private var health = maxHealth

    // Methods
    open fun tick() {}
    abstract fun die()
    fun alterHealth(amount: Int) : Int {
        health -= amount
        if (health <= 0) { die() }
        if (health > maxHealth) { health = maxHealth }
        return health
    }
    fun getHealth() : Int {
        return health
    }
}
