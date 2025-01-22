package org.nullversionnova.properties

import kotlinx.serialization.Serializable

@Serializable
data class ValueProperty(var property: String, var value: Int)
