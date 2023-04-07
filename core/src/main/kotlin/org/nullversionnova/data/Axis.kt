package org.nullversionnova.data

enum class Axis {
    X, Y, Z;
    fun direction(polarity: Boolean) : Direction {
        return when (this) {
            X -> if (polarity) { Direction.EAST } else { Direction.WEST }
            Y -> if (polarity) { Direction.NORTH } else { Direction.SOUTH }
            Z -> if (polarity) { Direction.UP } else { Direction.DOWN }
        }
    }
}
