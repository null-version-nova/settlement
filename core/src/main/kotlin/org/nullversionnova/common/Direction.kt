package org.nullversionnova.common

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
    fun visPolarity() : Boolean {
        return when (this) {
            NORTH, EAST, DOWN -> true
            SOUTH, WEST, UP -> false
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

}
