package org.nullversionnova.server.base.tiles

import org.nullversionnova.data.Axis
import org.nullversionnova.data.IntegerVector3

data class TileColumn(override val location : IntegerVector3, val height : Int, val axis: Axis, override val tile : TileInstance) : TileStorage {
    fun split() : MutableSet<TileUnit> {
        val set = mutableSetOf(TileUnit(location,tile))
        for (i in 1..height) {
            set.add(TileUnit(location.getNewWithSetAxis(i,axis),tile))
        }
        return set
    }
}
