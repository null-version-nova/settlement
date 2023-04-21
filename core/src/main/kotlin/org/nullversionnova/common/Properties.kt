package org.nullversionnova.common

open class Properties {
    // Members
    private val values: MutableMap<Identifier, Number> = mutableMapOf()
    private val properties: MutableSet<Identifier> = mutableSetOf()

    // Methods
    open operator fun get(identifier: Identifier) : Number? { return values[identifier] }
    open operator fun get(identifier: String) : Number? { return this[Identifier(identifier)] }
    open operator fun set(identifier: Identifier, number: Number) { values[identifier] = number }
    open operator fun set(identifier: String, number: Number) { values[Identifier(identifier)] = number }
    open fun hasValue(identifier: Identifier) : Boolean { return values.containsKey(identifier) }
    open fun hasValue(identifier: String) : Boolean { return hasValue(Identifier(identifier)) }
    open fun hasProperty(identifier: Identifier) : Boolean { return properties.contains(identifier) }
    open fun hasProperty(identifier: String) : Boolean { return hasProperty(Identifier(identifier)) }
    open fun make(identifier: Identifier) : Boolean { return properties.add(identifier) }
    open fun make(identifier: String) : Boolean { return make(Identifier(identifier)) }
    open fun makeNot(identifier: Identifier) : Boolean { return properties.remove(identifier) }
    open fun makeNot(identifier: String) : Boolean { return makeNot(Identifier(identifier)) }
}
