package org.nullversionnova.common

enum class Axis {
    X, Y, Z;
    fun direction(polarity: Boolean) : Direction {
        return when (this) {
            X -> if (polarity) { Direction.EAST } else { Direction.WEST }
            Y -> if (polarity) { Direction.NORTH } else { Direction.SOUTH }
            Z -> if (polarity) { Direction.UP } else { Direction.DOWN }
        }
    }
    fun getOtherPair() : Pair<Axis,Axis> {
        val first = when (this) {
            X -> Y
            Y -> X
            Z -> X
        }
        val second = when (this) {
            X -> Z
            Y -> Z
            Z -> Y
        }
        return Pair(first,second)
    }
}
