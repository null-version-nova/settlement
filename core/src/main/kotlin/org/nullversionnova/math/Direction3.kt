package org.nullversionnova.math

enum class Direction3 {
    NORTH, SOUTH, EAST, WEST, UP, DOWN;
    fun axis() : Axis3 {
        return when (this) {
            NORTH, SOUTH -> Axis3.Y
            EAST, WEST -> Axis3.X
            UP, DOWN -> Axis3.Z
        }
    }
    fun getOtherAxes() : Pair<Axis3, Axis3> {
        return axis().getOtherPair()
    }
    fun perpendicularAxis(axis: Axis2) : Axis3 {
        return axis().perpendicular(axis)
    }
    fun perpendicular(direction: Direction2) : Direction3 {
        return axis().perpendicular(direction.axis()).direction(!direction.polarity().xor(polarity()))
    }
    fun perpendicular(direction: Direction3) : Direction2? {
        return axis().perpendicular(direction.axis())?.direction(!direction.polarity().xor(polarity()))
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
    fun clockwise() : Direction3 {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
            UP -> UP
            DOWN -> DOWN
        }
    }
    fun counterClockwise() : Direction3 {
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
        return this.axis() == Axis3.Z
    }

}
