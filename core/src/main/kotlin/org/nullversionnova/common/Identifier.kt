package org.nullversionnova.common

import org.nullversionnova.server.engine.Engine

data class Identifier(var pack: String = Engine.pack_identifier, var name: String = "default") {
    constructor(identifier: String) : this(identifier.substringBefore(':'), identifier.substringAfter(':'))
    override fun toString() : String { return "$pack:$name" }
    override operator fun equals(other: Any?): Boolean {
        if (other is String) {
            return this == Identifier(other)
        }
        return super.equals(other)
    }
    override fun hashCode(): Int {
        var result = pack.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
