package org.nullversionnova.common

data class IntVector2(var x: Int = 0, var y: Int = 0) {
    constructor(intVector3: IntVector3, axis: Axis = Axis.Z) : this() {
        val pair = axis.getOtherPair()
        x = intVector3.getAxis(pair.first)
        y = intVector3.getAxis(pair.second)
    }
    fun getAxisFromInt(axis: Int) : Int {
        return when (axis) {
            0 -> {
                x
            }
            else -> {
                y
            }
        }
    }
}
