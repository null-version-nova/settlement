package org.nullversionnova.properties

import kotlinx.serialization.Serializable
import org.nullversionnova.Registries
import org.nullversionnova.registry.Identifier

@Serializable
class InheritingPropertiesJSON (
    val values: Array<ValueProperty> = arrayOf(),
    private val properties: Array<String> = arrayOf(),
    private val propertyNegation: Array<String> = arrayOf(),
    private val parent : String? = null
) {
    fun cast() : MutableInheritingProperties<Int> {
        val item = MutableInheritingProperties<Int>(parent?.let { Registries.materialRegistry[it] } as InheritingProperties<Int>?)
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
    fun cast(identifier: Identifier) : MutableInheritingProperties<Int> {
        val item = MutableInheritingProperties<Int>(parent?.let { Registries.materialRegistry[it] } as InheritingProperties<Int>?)
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
