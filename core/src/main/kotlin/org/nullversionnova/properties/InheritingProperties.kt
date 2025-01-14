package org.nullversionnova.properties

import org.nullversionnova.registry.Identifier

open class InheritingProperties<T>(protected open val parent : InheritingProperties<T>? = null, values: Map<Identifier,T> = emptyMap(), properties: Set<Identifier> = emptySet(), protected open val propertyNegation: Set<Identifier> = setOf()) : MappedProperties<T>(values,properties) {
    override fun get(identifier: Identifier): T? {
        return if (super.get(identifier) == null) {
            if (parent == null) {
                null
            } else {
                parent!![identifier]
            }
        } else {
            super.get(identifier)
        }
    }
    override fun hasProperty(identifier: Identifier): Boolean {
        return if (super.hasProperty(identifier)) {
            true
        } else if (propertyNegation.contains(identifier) || parent == null) {
            false
        } else {
            parent!!.hasProperty(identifier)
        }
    }
}
