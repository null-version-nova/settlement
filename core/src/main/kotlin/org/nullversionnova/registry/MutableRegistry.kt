package org.nullversionnova.registry

class MutableRegistry<T> : Registry<T> {
    private val registry = mutableMapOf<Identifier,T>()

    private val callbacks = mutableListOf<() -> Unit>()
    private var _registered = false
    val registered : Boolean
        get() = _registered

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

    override fun register() {
        _registered = true
        callbacks.forEach { it.invoke() }
    }

    override fun dispose() {
        _registered = false
        registry.clear()
    }

    override fun listen(callback: () -> Unit) {
        callbacks.add(callback)
    }
}
