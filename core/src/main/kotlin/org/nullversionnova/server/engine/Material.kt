package org.nullversionnova.server.engine

import org.nullversionnova.common.Identifier

class Material {
    val valueProperties = mutableMapOf<Identifier,Number>()
    val properties = mutableSetOf<Identifier>()
    var parent : Identifier? = null
}
