package org.nullversionnova.world

import org.nullversionnova.registry.Identifier

abstract class AbstractRegistryObject {
    private var _identifier : Identifier? = null
    fun register(identifier: Identifier) {
        _identifier = identifier
    }
    val identifier: Identifier
        get() = _identifier ?: throw Exception("Identifier accessed before registration!")
}
