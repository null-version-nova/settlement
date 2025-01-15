package org.nullversionnova.registry

/**
 * @interface Registry
 *
 */
interface Registry<T> : Map<Identifier,T> {
    operator fun get(string: String) = this[Identifier(string)]
    fun register()
    fun dispose()
    fun listen(callback: () -> Unit)
}
