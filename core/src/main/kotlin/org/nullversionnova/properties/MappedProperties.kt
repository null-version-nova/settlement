package org.nullversionnova.properties

import org.nullversionnova.registry.Identifier

open class MappedProperties<T>(protected open val values: Map<Identifier,T> = emptyMap(), protected open val properties: Set<Identifier> = emptySet()) : Properties<T> {
    override fun get(identifier: Identifier): T? { return values[identifier] }
    override fun hasValue(identifier: Identifier): Boolean { return values.containsKey(identifier) }
    override fun hasProperty(identifier: Identifier): Boolean { return properties.contains(identifier) }
    override fun values(): Collection<Identifier> {
        return values.keys
    }
    override fun properties(): Collection<Identifier> {
        return properties
    }
}
