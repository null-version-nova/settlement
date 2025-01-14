package org.nullversionnova.properties

import org.nullversionnova.registry.Identifier

interface Properties<T> {
    operator fun get(identifier: Identifier) : T?
    operator fun get(identifier: String) : T? { return this[Identifier(identifier)] }
    fun hasValue(identifier: Identifier) : Boolean
    fun hasValue(identifier: String) : Boolean { return hasValue(Identifier(identifier)) }
    fun hasProperty(identifier: Identifier) : Boolean
    fun hasProperty(identifier: String) : Boolean { return hasProperty(Identifier(identifier)) }
    fun values() : Collection<Identifier>
    fun properties(): Collection<Identifier>
}
