package org.nullversionnova.math

enum class Axis2 {
    X, Y;
    fun direction(polarity: Boolean) : Direction2 {
        return when (this) {
            X -> when (polarity) {
                true -> Direction2.RIGHT
                false -> Direction2.LEFT
            }
            Y -> when (polarity) {
                true -> Direction2.UP
                false -> Direction2.DOWN
            }
        }
    }
}
