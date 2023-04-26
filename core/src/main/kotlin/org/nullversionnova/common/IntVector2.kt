package org.nullversionnova.common

data class IntVector2(var x: Int, var y: Int) {
    constructor() : this(0,0)
    constructor(xf: Number, yf: Number) : this(xf.toInt(),yf.toInt())
    constructor(intVector3: IntVector3, axis: Axis3 = Axis3.Z) : this() {
        val pair = axis.getOtherPair()
        x = intVector3.getAxis(pair.first)
        y = intVector3.getAxis(pair.second)
    }
    fun getAxis(axis: Axis2) : Int {
        return when (axis) {
            Axis2.X -> x
            Axis2.Y -> y
        }
    }
}
