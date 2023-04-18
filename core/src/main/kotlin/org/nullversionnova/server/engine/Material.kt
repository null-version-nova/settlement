package org.nullversionnova.server.engine

import org.nullversionnova.common.Identifier

class Material {
    val valueProperties = mutableMapOf<Identifier,Number>()
    val propertyAffirmation = mutableSetOf<Identifier>()
    val propertyNegation = mutableSetOf<Identifier>()
    var parent : Identifier? = null
}
