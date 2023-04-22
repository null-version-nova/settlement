package org.nullversionnova.common.properties

import org.nullversionnova.common.Identifier

open class MutableInheritingProperties<T>(parent: InheritingProperties<T>? = null) : MutableProperties<T>, InheritingProperties<T>(parent) {
    // Members
    override val values = mutableMapOf<Identifier,T>()
    override val properties = mutableSetOf<Identifier>()
    override val propertyNegation = mutableSetOf<Identifier>()

    // Methods
    override fun set(identifier: Identifier, value: T) { values[identifier] = value }
    override fun make(identifier: Identifier): Boolean {
        propertyNegation.remove(identifier)
        return properties.add(identifier)
    }
    override fun makeNot(identifier: Identifier): Boolean {
        return if (hasProperty(identifier)) {
            propertyNegation.add(identifier)
            true
        } else {
            false
        }
    }
    override fun staticCopy(): InheritingProperties<T> {
        return InheritingProperties(parent,values.toMap(),properties.toSet(),propertyNegation.toSet())
    }
}
