package org.nullversionnova.properties

import org.nullversionnova.registry.Identifier

class BoundedProperty(val property: Identifier, val default: Number, val minimum: Number? = null, val maximum: Number? = null) {
    constructor(property: String, default: Number, minimum: Number? = null, maximum: Number? = null) : this(
        Identifier(
            property
        ),default,minimum,maximum)
}
