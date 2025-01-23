package org.nullversionnova

import org.nullversionnova.registry.Identifier

class ServerRegistry {
    private val valueProperties = mutableMapOf<Identifier,Number>()

    fun addValueProperty(identifier: Identifier, default: Number) { valueProperties[identifier] = default }
}
