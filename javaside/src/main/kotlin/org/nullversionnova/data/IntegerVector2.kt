package org.nullversionnova.data

data class IntegerVector2(var x: Int = 0, var y: Int = 0) {
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
