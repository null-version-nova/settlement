package org.nullversionnova.common

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.world.WorldCell

data class IntVector3(var x: Int, var y: Int, var z: Int) {

    // Constructors
    constructor() : this(0,0,0)
    constructor(xf : Number, yf: Number, zf : Number) : this(xf.toInt(),yf.toInt(),zf.toInt())
    constructor(vector: Vector3) : this(vector.x,vector.y,vector.z)
    constructor(axis: Axis3, scale: Int = 1) : this() { setAxis(scale,axis) }
    constructor(scale: Int, axis: Axis3 = Axis3.X) : this(axis, scale)

    // Operators
    operator fun plus(vector: IntVector3) : IntVector3 {
        return IntVector3(x + vector.x, y+vector.y, z+vector.z)
    }
    operator fun plusAssign(other: IntVector3) {
        x += other.x
        y += other.y
        z += other.z
    }
    operator fun minus(other: IntVector3) : IntVector3 {
        return this + -other
    }
    operator fun minusAssign(other: IntVector3) {
        this += -other
    }
    operator fun times(other: Number) : IntVector3 {
        return IntVector3(x * other.toInt(), y * other.toInt(), z * other.toInt())
    }
    operator fun timesAssign(other: Number) {
        x *= other.toInt()
        y *= other.toInt()
        z *= other.toInt()
    }
    operator fun div(other: Number) : IntVector3 {
        return IntVector3(x / other.toInt(), y / other.toInt(), z / other.toInt())
    }
    operator fun divAssign(other: Number) {
        x /= other.toInt()
        y /= other.toInt()
        z /= other.toInt()
    }
    operator fun unaryMinus() : IntVector3 {
        return this * -1
    }

    // Functions
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
    operator fun get(axis: Axis3) : Int {
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
    fun toGlobal(local: IntVector3 = IntVector3()) : IntVector3 { return this * WorldCell.CELL_SIZE - local }
    fun toGlobal(localX: Number, localY: Number, localZ: Number) : IntVector3 { return toGlobal(IntVector3(localX,localY,localZ)) }
    fun toLocal() : IntVector3 { return IntVector3(x.mod(WorldCell.CELL_SIZE),y.mod(WorldCell.CELL_SIZE),z.mod(WorldCell.CELL_SIZE)) }

    companion object {
        val UNIT = IntVector3(1,0,0)
        val ZERO = IntVector3()
    }
}
