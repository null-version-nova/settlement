package org.nullversionnova.registry

import kotlin.reflect.KProperty

class RegistryDelegate<T>(val registry: Registry<T>, val identifier: Identifier) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return registry[identifier] ?: throw Exception("Object at $identifier not registered")
    }
}
