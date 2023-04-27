package org.nullversionnova.common

import org.nullversionnova.server.world.WorldCell

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
    fun toCell() : IntVector2 {
        return IntVector2(
            x.floorDiv(WorldCell.CELL_SIZE),
            y.floorDiv(WorldCell.CELL_SIZE),
        )
    }
    fun toGlobal(local: IntVector2 = IntVector2()) : IntVector2 {
        return IntVector2(
            x * WorldCell.CELL_SIZE + local.x,
            y * WorldCell.CELL_SIZE + local.y
        )
    }
    fun toGlobal(localX: Number, localY: Number) : IntVector2 { return toGlobal(IntVector2(localX,localY)) }
    fun toLocal() : IntVector2 {
        return IntVector2(
            x - toCell().toGlobal().x,
            y - toCell().toGlobal().y
        )
    }
}
