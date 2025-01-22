package org.nullversionnova.registry

/**
 * A mapped set that is initialized once and used often!
 *
 */
interface Registry<T> : Map<Identifier,T> {
    val registered: Boolean
    operator fun get(string: String) = this[Identifier(string)]
    fun register()
    fun dispose()
    fun listen(callback: () -> Unit)
}
