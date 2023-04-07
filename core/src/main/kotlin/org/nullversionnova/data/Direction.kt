package org.nullversionnova.data

enum class Direction {
    NORTH, SOUTH, EAST, WEST, UP, DOWN;
    fun axis() : Axis {
        return when (this) {
            NORTH, SOUTH -> Axis.Y
            EAST, WEST -> Axis.X
            UP, DOWN -> Axis.Z
        }
    }
    fun polarity() : Boolean {
        return when (this) {
            NORTH, EAST, UP -> true
            SOUTH, WEST, DOWN -> false
        }
    }
    fun clockwise() : Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
            UP -> UP
            DOWN -> DOWN
        }
    }
    fun counterClockwise() : Direction {
        return when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
            UP -> UP
            DOWN -> DOWN
        }
    }
    fun isVertical() : Boolean {
        return this.axis() == Axis.Z
    }
    fun getOtherPair() : Pair<Axis,Axis> {
        val first = when (this.axis()) {
            Axis.X -> Axis.Y
            Axis.Y -> Axis.X
            Axis.Z -> Axis.X
        }
        val second = when (this.axis()) {
            Axis.X -> Axis.Z
            Axis.Y -> Axis.Z
            Axis.Z -> Axis.Y
        }
        return if (this.polarity()) { Pair(first,second) } else { Pair(second,first) }
    }
}
