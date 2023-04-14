package org.nullversionnova.common

data class Properties(val values: Array<Pair<Identifier,Number>> = arrayOf(), val properties: Array<Identifier> = arrayOf(), val parent : Identifier? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Properties

        if (!values.contentEquals(other.values)) return false
        if (!properties.contentEquals(other.properties)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = values.contentHashCode()
        result = 31 * result + properties.contentHashCode()
        return result
    }
}
