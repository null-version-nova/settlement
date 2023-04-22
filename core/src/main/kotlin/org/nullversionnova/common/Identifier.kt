package org.nullversionnova.common

import org.nullversionnova.server.engine.Engine

class Identifier(var pack: String? = Engine.pack_identifier, var name: String = "default") {
    constructor(identifier: String) : this(identifier.substringBefore(':'), identifier.substringAfter(':'))
    override fun toString() : String { return "$pack:$name" }
}
