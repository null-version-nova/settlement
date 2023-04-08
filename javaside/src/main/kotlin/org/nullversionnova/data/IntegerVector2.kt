package org.nullversionnova.data

data class IntegerVector2(var x: Int = 0, var y: Int = 0) {
    constructor(integerVector3: IntegerVector3, axis: Axis = Axis.Z) : this() {
        val pair = axis.getOtherPair()
        x = integerVector3.getAxis(pair.first)
        y = integerVector3.getAxis(pair.second)
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
