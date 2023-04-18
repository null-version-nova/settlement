package org.nullversionnova.common

data class Properties(
    val values: Array<ValueProperty> = arrayOf(),
    val propertyAffirmation: Array<Identifier> = arrayOf(),
    val propertyNegation: Array<Identifier> = arrayOf(),
    val parent : String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Properties

        if (!values.contentEquals(other.values)) return false
        if (!propertyAffirmation.contentEquals(other.propertyAffirmation)) return false

        return true
    }
    override fun hashCode(): Int {
        var result = values.contentHashCode()
        result = 31 * result + propertyAffirmation.contentHashCode()
        return result
    }
}
