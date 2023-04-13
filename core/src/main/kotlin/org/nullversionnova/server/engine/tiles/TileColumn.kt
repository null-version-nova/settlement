package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Axis
import org.nullversionnova.common.IntVector3

data class TileColumn(override val location : IntVector3, val height : Int, val axis: Axis, override val tile : TileInstance) :
    TileStorage {
    fun split() : MutableSet<TileUnit> {
        val set = mutableSetOf(TileUnit(location,tile))
        for (i in 1..height) {
            set.add(TileUnit(location.getNewWithSetAxis(i,axis),tile))
        }
        return set
    }
}
