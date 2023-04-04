package org.nullversionnova.data

import org.nullversionnova.server.base.Base

data class Identifier(val pack: String, val name: String) {
    constructor(name: String) : this(Base.pack_identifier,name)
}
