package org.nullversionnova.common.properties

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.ValueProperty
import org.nullversionnova.server.ServerRegistry

class InheritingPropertiesJSON (
    val values: Array<ValueProperty> = arrayOf(),
    private val properties: Array<String> = arrayOf(),
    private val propertyNegation: Array<String> = arrayOf(),
    private val parent : String? = null
) {
    fun cast(registry: ServerRegistry) : MutableInheritingProperties<Number> {
        val item = MutableInheritingProperties(parent?.let { registry.getMaterial(it) })
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
    fun cast(registry: ServerRegistry, identifier: Identifier) : MutableInheritingProperties<Number> {
        val item = MutableInheritingProperties(parent?.let { registry.getMaterial(it) })
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
