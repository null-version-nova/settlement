package org.nullversionnova.registry

class ConstructorRegistry<T>(private val registry : MutableRegistry<T>) : Registry<T> by registry {
    override fun register() {
        TODO("Not yet implemented")
    }
    fun register(identifier: Identifier, callback: () -> T) {

    }
}
