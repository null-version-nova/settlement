package org.nullversionnova.common

import org.nullversionnova.server.engine.Engine

data class Identifier(var pack: String? = Engine.pack_identifier, var name: String = "default") {
    constructor(identifier: String) : this() {
        pack = identifier.substringBefore(':')
        name = identifier.substringAfter(':')
    }
    override fun toString() : String { return "$pack:$name" }
}
