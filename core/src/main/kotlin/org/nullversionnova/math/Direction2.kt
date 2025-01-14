package org.nullversionnova.math

enum class Direction2 {
    UP, DOWN, LEFT, RIGHT;

    fun axis() : Axis2 {
        return when (this) {
            LEFT, RIGHT -> Axis2.X
            UP, DOWN -> Axis2.Y
        }
    }
    fun polarity() : Boolean {
        return when (this) {
            UP, RIGHT -> true
            DOWN, LEFT -> false
        }
    }
}
