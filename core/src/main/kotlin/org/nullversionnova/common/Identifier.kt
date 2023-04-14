package org.nullversionnova.common

import org.nullversionnova.server.engine.Engine

data class Identifier(val pack: String? = Engine.pack_identifier, val name: String = "default") {
    constructor(name: String) : this(null,name)
}
