package org.nullversionnova.server.base.entities

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3

class StaticEntity(identifier: Identifier, position: IntegerVector3 = IntegerVector3(0,0,0)) : Entity(identifier) {
    // Methods
    override fun die() {
        TODO("Not yet implemented")
    }
}
