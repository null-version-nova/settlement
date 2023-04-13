package org.nullversionnova.common

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.WorldCell

data class IntVector3(var x: Int = 0, var y: Int = 0, var z: Int = 0) {
    constructor(xf : Float, yf: Float, zf : Float) : this(xf.toInt(),yf.toInt(),zf.toInt())
    constructor(vector: Vector3) : this(vector.x,vector.y,vector.z)
    fun toVector3() : Vector3 {
        return Vector3(x.toFloat(),y.toFloat(),z.toFloat())
    }
    fun getAxis(axis: Axis) : Int {
        return when (axis) {
            Axis.X -> x
            Axis.Y -> y
            Axis.Z -> z
        }
    }
    fun setAxis(newValue : Int, axis: Axis) {
        when (axis) {
            Axis.X -> x = newValue
            Axis.Y -> y = newValue
            Axis.Z -> z = newValue
        }
    }
    fun getNewWithSetAxis(newValue: Int, axis: Axis) : IntVector3 {
        val vector = this.copy()
        vector.setAxis(vector.getAxis(axis) + newValue, axis)
        return vector
    }
    fun reflectAcrossCell(axis: Axis) { setAxis(WorldCell.CELL_SIZE - getAxis(axis),axis) }
}
