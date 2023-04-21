package org.nullversionnova.common

import org.nullversionnova.server.ServerRegistry

class InheritingPropertiesJSON (
    val values: Array<ValueProperty> = arrayOf(),
    val properties: Array<String> = arrayOf(),
    val propertyNegation: Array<String> = arrayOf(),
    val parent : String? = null
) {
    fun cast(registry: ServerRegistry) : InheritingProperties {
        val item = InheritingProperties(parent?.let { registry.getMaterial(it) })
        for (i in values) {
            item[i.property] = i.value
        }
        for (i in properties) {
            item.make(i)
        }
        for (i in propertyNegation) {
            item.makeNot(i)
        }
        return item
    }
    fun cast(registry: ServerRegistry, identifier: Identifier) : InheritingProperties {
        val item = InheritingProperties(parent?.let { registry.getMaterial(it) })
        for (i in values) {
            item[i.property] = i.value
        }
        for (i in properties) {
            item.make(i)
        }
        for (i in propertyNegation) {
            item.makeNot(i)
        }
        item.make(identifier)
        return item
    }
}
