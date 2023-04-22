package org.nullversionnova.common.properties

import org.nullversionnova.common.Identifier

open class MutableMappedProperties<T> : MutableProperties<T>, MappedProperties<T>() {
    override val values = mutableMapOf<Identifier,T>()
    override val properties = mutableSetOf<Identifier>()
    override fun set(identifier: Identifier, value: T) { values[identifier] = value }
    override fun make(identifier: Identifier): Boolean { return properties.add(identifier) }
    override fun makeNot(identifier: Identifier): Boolean { return properties.remove(identifier) }
    override fun staticCopy(): Properties<T> { return MappedProperties(values.toMap(),properties.toSet()) }
}
