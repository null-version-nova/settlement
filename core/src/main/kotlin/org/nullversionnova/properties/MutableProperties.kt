package org.nullversionnova.properties

import org.nullversionnova.registry.Identifier

interface MutableProperties<T> : Properties<T> {
    operator fun set(identifier: Identifier, value: T)
    operator fun set(identifier: String, value: T) { this[Identifier(identifier)] = value }
    fun make(identifier: Identifier) : Boolean
    fun make(identifier: String) : Boolean { return make(Identifier(identifier)) }
    fun makeNot(identifier: Identifier) : Boolean
    fun makeNot(identifier: String) : Boolean { return makeNot(Identifier(identifier)) }
    fun staticCopy() : Properties<T>
}
