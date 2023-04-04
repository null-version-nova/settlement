package org.nullversionnova.data

data class IntegerVector2(var x: Int, var y: Int) {
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
