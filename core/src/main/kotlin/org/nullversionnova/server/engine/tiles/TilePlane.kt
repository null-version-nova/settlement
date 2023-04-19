package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Axis
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.engine.GameObject

data class TilePlane(override val location : IntVector3, var height : Int, var width : Int, val axis: Axis, override val tile : GameObject) : TileStorage {
    override fun tick(server: Server) {
        TODO("Not yet implemented")
    }
    fun split() : MutableSet<TileUnit> {
        val set = mutableSetOf(TileUnit(location,tile))
        for (i in 1 until height) {
            for (j in 1 until width) {
                set.add(TileUnit(location.getNewWithSetAxis(i,axis.getOtherPair().first).getNewWithSetAxis(j,axis.getOtherPair().second),tile))
            }
        }
        return set
    }
    fun splitToColumns() : MutableSet<TileColumn> {
        val newPrimaryAxis = if (height >= width) { axis.getOtherPair().first } else { axis.getOtherPair().second }
        val newSecondaryAxis = if (height < width) { axis.getOtherPair().first } else { axis.getOtherPair().second }
        val newHeight = if (height >= width) { height } else { width }
        val newWidth = if (height < width) { height } else { width }
        val set = mutableSetOf(TileColumn(location,newHeight,newPrimaryAxis,tile))
        for (i in 1 until newWidth) {
            set.add(TileColumn(location.getNewWithSetAxis(i,newSecondaryAxis),newHeight,newPrimaryAxis,tile))
        }
        return set
    }
    fun getAxis(axis: Axis) : Int {
        return if (this.axis.getOtherPair().first == axis) { height }
        else if (this.axis.getOtherPair().second == axis) { width }
        else { 1 }
    }
    fun alterAxis(alter: Int, axis: Axis) : Boolean {
        if (axis == this.axis) {
            return false
        }
        if (this.axis.getOtherPair().first == axis) { height = alter }
        else if (this.axis.getOtherPair().second == axis) { width = alter }
        return true
    }
    override fun overlap(position: IntVector3): Boolean {
        return if (location.getAxis(axis) != position.getAxis(axis)) {
            false
        } else if (location.getAxis(axis.getOtherPair().first) > position.getAxis(axis.getOtherPair().first)) {
            false
        } else if (location.getAxis(axis.getOtherPair().first) + height <= position.getAxis(axis.getOtherPair().first)) {
            false
        } else if (location.getAxis(axis.getOtherPair().second) > position.getAxis(axis.getOtherPair().second)) {
            false
        } else location.getAxis(axis.getOtherPair().second) + width > position.getAxis(axis.getOtherPair().second)
    }
}
