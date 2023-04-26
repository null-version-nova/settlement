package org.nullversionnova.common

enum class Axis3 {
    X, Y, Z;
    fun direction(polarity: Boolean) : Direction3 {
        return when (this) {
            X -> if (polarity) { Direction3.EAST } else { Direction3.WEST }
            Y -> if (polarity) { Direction3.NORTH } else { Direction3.SOUTH }
            Z -> if (polarity) { Direction3.UP } else { Direction3.DOWN }
        }
    }
    fun getOtherPair() : Pair<Axis3,Axis3> {
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
    fun perpendicular(axis: Axis2) : Axis3 {
        return when (this) {
            X -> when (axis) {
                Axis2.X -> Y
                Axis2.Y -> Z
            }
            Y -> when (axis) {
                Axis2.X -> X
                Axis2.Y -> Z
            }
            Z -> when (axis) {
                Axis2.X -> X
                Axis2.Y -> Y
            }
        }
    }
    fun perpendicular(axis: Axis3) : Axis2? {
        return when (this) {
            X -> when (axis) {
                X -> null
                Y -> Axis2.X
                Z -> Axis2.Y
            }
            Y -> when (axis) {
                X -> Axis2.X
                Y -> null
                Z -> Axis2.Y
            }
            Z -> when (axis) {
                X -> Axis2.X
                Y -> Axis2.Y
                Z -> null
            }
        }
    }
}
