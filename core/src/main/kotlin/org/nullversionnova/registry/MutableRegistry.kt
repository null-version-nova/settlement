package org.nullversionnova.registry

class MutableRegistry<T> : Registry<T> {
    private val registry = mutableMapOf<Identifier,T>()

    override val entries = registry.entries
    override val keys = registry.keys
    override val size = registry.size
    override val values = registry.values
    override fun containsKey(key: Identifier) = registry.containsKey(key)
    override fun containsValue(value: T) = registry.containsValue(value)
    override fun get(key: Identifier) = registry[key]
    override fun isEmpty() = registry.isEmpty()

    fun register(identifier: Identifier, obj: T) : T {
        if (containsKey(identifier)) throw Exception()
        registry[identifier] = obj
        return obj
    }
}
