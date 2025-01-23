package org.nullversionnova.registry

import org.nullversionnova.world.AbstractRegistryObject

class ConstructorRegistry<T: AbstractRegistryObject>(private val registry : MutableRegistry<T>) : Registry<T> by registry {
    private val callbacks = mutableMapOf<Identifier,() -> T>()
    override fun register() {
        callbacks.forEach {
            registry.register(it.key,it.value().also { obj -> obj.register(it.key) })
        }
    }
    fun register(identifier: Identifier, callback: () -> T) : RegistryDelegate<T> {
        callbacks[identifier] = callback
        return RegistryDelegate(this,identifier)
    }
    fun register(identifier: String, callback: () -> T) : RegistryDelegate<T> {
        return register(Identifier(identifier),callback)
    }
}
