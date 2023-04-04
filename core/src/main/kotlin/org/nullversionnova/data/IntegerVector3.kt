package org.nullversionnova.data

import com.badlogic.gdx.math.Vector3

data class IntegerVector3(val x: Int, val y: Int, val z: Int) {
    constructor(xf : Float, yf: Float, zf : Float) : this(xf.toInt(),yf.toInt(),zf.toInt())
    constructor(vector: Vector3) : this(vector.x,vector.y,vector.z)
    fun toVector3() : Vector3 {
        return Vector3(x.toFloat(),y.toFloat(),z.toFloat())
    }
    fun getAxisFromInt(axis: Int) : Int {
        return when (axis) {
            0 -> {
                x
            }
            1 -> {
                y
            }
            else -> {
                z
            }
        }
    }
}
