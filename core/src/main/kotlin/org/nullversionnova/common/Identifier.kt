package org.nullversionnova.common

import org.nullversionnova.server.settlement.Settlement

data class Identifier(val pack: String = Settlement.pack_identifier, val name: String) {
    constructor(name: String) : this(Settlement.pack_identifier,name)
}
