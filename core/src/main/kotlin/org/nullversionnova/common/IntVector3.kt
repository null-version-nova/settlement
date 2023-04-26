package org.nullversionnova.common

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.world.WorldCell

data class IntVector3(var x: Int = 0, var y: Int = 0, var z: Int = 0) {
    constructor(xf : Number, yf: Number, zf : Number) : this(xf.toInt(),yf.toInt(),zf.toInt())
    constructor(vector: Vector3) : this(vector.x,vector.y,vector.z)
    fun toVector3() : Vector3 {
        return Vector3(x.toFloat(),y.toFloat(),z.toFloat())
    }
    fun getAxis(axis: Axis3) : Int {
        return when (axis) {
            Axis3.X -> x
            Axis3.Y -> y
            Axis3.Z -> z
        }
    }
    fun setAxis(newValue : Number, axis: Axis3) {
        when (axis) {
            Axis3.X -> x = newValue.toInt()
            Axis3.Y -> y = newValue.toInt()
            Axis3.Z -> z = newValue.toInt()
        }
    }
    fun getNewWithSetAxis(newValue: Number, axis: Axis3) : IntVector3 {
        val vector = this.copy()
        vector.setAxis(vector.getAxis(axis) + newValue.toInt(), axis)
        return vector
    }
    fun toCell() : IntVector3 {
        return IntVector3(
            x.floorDiv(WorldCell.CELL_SIZE),
            y.floorDiv(WorldCell.CELL_SIZE),
            z.floorDiv(WorldCell.CELL_SIZE)
        )
    }
    fun toGlobal(local: IntVector3 = IntVector3()) : IntVector3 {
        return IntVector3(
            x * WorldCell.CELL_SIZE + local.x,
            y * WorldCell.CELL_SIZE + local.y,
            z * WorldCell.CELL_SIZE + local.z
        )
    }
    fun toGlobal(localX: Number, localY: Number, localZ: Number) : IntVector3 { return IntVector3(localX,localY,localZ) }
    fun toLocal() : IntVector3 {
        return IntVector3(
            x - toCell().toGlobal().x,
            y - toCell().toGlobal().y,
            z - toCell().toGlobal().z
        )
    }
    fun reflectAcrossCell(axis: Axis3) { setAxis(WorldCell.CELL_SIZE - getAxis(axis),axis) }
}
