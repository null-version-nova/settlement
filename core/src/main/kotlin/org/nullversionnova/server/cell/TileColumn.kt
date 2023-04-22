package org.nullversionnova.server.cell

import org.nullversionnova.common.Axis
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.engine.tiles.TickableTile
import org.nullversionnova.server.engine.tiles.TileInstance

data class TileColumn(override val location : IntVector3, var height : Int, val axis: Axis, override val tile : TileInstance) :
    TileStorage {
    fun split() : MutableSet<TileUnit> {
        val set = mutableSetOf(TileUnit(location,tile))
        for (i in 1 until height) {
            set.add(TileUnit(location.getNewWithSetAxis(i,axis),tile))
        }
        return set
    }
    fun getAxis(axis: Axis) : Int {
        return if (axis == this.axis) {
            height
        } else {
            1
        }
    }
    override fun overlap(position: IntVector3) : Boolean {
        return location.getAxis(axis.getOtherPair().first) == position.getAxis(axis.getOtherPair().first) && location.getAxis(axis.getOtherPair().second) == position.getAxis(axis.getOtherPair().second) && position.getAxis(axis) >= location.getAxis(axis) && position.getAxis(axis) < location.getAxis(axis) + height
    }
    override fun tick(server: Server) {
        val tile = tile.getTile(server.registry)
        if (tile is TickableTile) {
            for (i in 0 until height) { tile.tick(location.getNewWithSetAxis(i,axis),server) }
        }
    }
}
