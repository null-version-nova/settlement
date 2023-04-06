package org.nullversionnova.server

import org.nullversionnova.SimplexNoise
import org.nullversionnova.data.IntegerVector3

class Generator (val location : IntegerVector3 = IntegerVector3()) {
    fun getHeight(xin : Double, yin : Double) : Double {
        return SimplexNoise.noise(xin / 50 + location.x * WorldCell.CELL_SIZE_X, yin / 50 + location.y * WorldCell.CELL_SIZE_Y)
    }
}
