package org.nullversionnova.registry

import java.io.File

open class ResourceRegistry<T>(private val registry: MutableRegistry<T>, val clientSide: Boolean, val path: String, val extension: String, val accessor: (File) -> T) : Registry<T> by registry {
    override fun register() {
        preRegister()
        if (clientSide) { clientSideNamespaces } else { serverSideNamespaces }.forEach { namespace ->
            namespace.resolve(path).listFiles().forEach { file ->
                if (file.extension == extension) {
                    registry.register(Identifier(namespace.name,file.nameWithoutExtension),accessor(file))
                }
            }
        }
        postRegister()
    }
    open fun preRegister() {}
    open fun postRegister() {}

    companion object {
        val clientSideNamespaces = File("client").listFiles()
        val serverSideNamespaces = File("server").listFiles()
    }
}
