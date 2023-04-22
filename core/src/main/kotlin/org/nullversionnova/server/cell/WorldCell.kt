package org.nullversionnova.server.cell

import org.nullversionnova.SimplexNoise
import org.nullversionnova.common.Axis
import org.nullversionnova.common.Axis.*
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.ServerRegistry
import org.nullversionnova.server.engine.tiles.*

class WorldCell (private val location: IntVector3) {
    // Members
    val tileMap = mutableMapOf<IntVector3, TileStorage>()
    private var loaded = false

    // Methods
    fun generate(registry: ServerRegistry) {
        loaded = true
        tileMap.clear()
        for (i in 0 until CELL_SIZE) {
            for (j in 0 until CELL_SIZE) {
                val height = getHeight(i.toDouble(),j.toDouble()).toInt()
                addColumn(IntVector3(i,j,0), height, Z, TileInstance(Identifier("settlement","rock")),registry)
                addColumn(IntVector3(i,j,height), SOIL_DEPTH, Z, TileInstance(Identifier("settlement","sand")),registry)
            }
        }
    }
    fun unload() {
        loaded = false
        tileMap.clear()
    }
    fun findTile(location: IntVector3) : TileStorage? {
        for (i in location.z downTo 0) {
            val scan = location.copy(z = i)
            if (tileMap[scan] != null) {
                return when (val result = tileMap[scan]) {
                    is TileUnit -> if (scan == location) { result } else { null }
                    is TileColumn -> if (scan.z + result.height > location.z) { result } else { null }
                    else -> null
                }
            }
        }
        return null
    }
    fun optimize(registry: ServerRegistry) { // oh holy fuck
//        var complete : Boolean
//        val toRemove = mutableSetOf<TileStorage>()
//        while (true) {
//            complete = true
//            for (i in tileMap) {
//                val tile = i.tile
//                val position = i.location
//                when (i) {
//                    is TileUnit -> {
//                        if (findTile(position.copy(x = position.x - 1))?.tile == tile) {
//                            remove(findTile(position.copy(x = position.x - 1))!!)
//                            toRemove.add(i)
//                            addColumn(position.copy(x = position.x - 1),2,X,tile,registry)
//                            complete = false
//                            break
//                        }
//                        if (findTile(position.copy(x = position.x + 1))?.tile == tile) {
//                            remove(findTile(position.copy(x = position.x + 1))!!)
//                            toRemove.add(i)
//                            addColumn(position,2,X,tile,registry)
//                            complete = false
//                            break
//                        }
//                        if (findTile(position.copy(y = position.y - 1))?.tile == tile) {
//                            remove(findTile(position.copy(y = position.y - 1))!!)
//                            toRemove.add(i)
//                            addColumn(position.copy(y = position.y - 1),2,Y,tile,registry)
//                            complete = false
//                            break
//                        }
//                        if (findTile(position.copy(y = position.y + 1))?.tile == tile) {
//                            remove(findTile(position.copy(y = position.y + 1))!!)
//                            toRemove.add(i)
//                            addColumn(position,2,Y,tile,registry)
//                            complete = false
//                            break
//                        }
//                        if (findTile(position.copy(z = position.z - 1))?.tile == tile) {
//                            remove(findTile(position.copy(z = position.z - 1))!!)
//                            toRemove.add(i)
//                            addColumn(position.copy(z = position.z - 1),2,Z,tile,registry)
//                            complete = false
//                            break
//                        }
//                        if (findTile(position.copy(z = position.z + 1))?.tile == tile) {
//                            remove(findTile(position.copy(z = position.z + 1))!!)
//                            toRemove.add(i)
//                            addColumn(position,2,Z,tile,registry)
//                            complete = false
//                            break
//                        }
//                    }
//                    is TileColumn -> {
//                        val height = i.height
//                        val newZ = i.axis
//                        val newX = i.axis.getOtherPair().first
//                        val newY = i.axis.getOtherPair().second
//                        if (findTile(position.getNewWithSetAxis(height,newZ))?.tile == tile) {
//                            val other = findTile(i.location.getNewWithSetAxis(i.height,newZ))
//                            if (other is TileColumn && other.axis == newZ) {
//                                i.height += other.height
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                            if (other is TileUnit) {
//                                i.height += 1
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                        }
//                        if (findTile(position.getNewWithSetAxis(-1,newZ))?.tile == tile) {
//                            val other = findTile(position.getNewWithSetAxis(-1,newZ))
//                            if (other is TileColumn && other.axis == newZ) {
//                                other.height += height
//                                toRemove.add(i)
//                                complete = false
//                                break
//                            }
//                            if (other is TileUnit) {
//                                i.location.setAxis(i.location.getAxis(newZ) - 1, newZ)
//                                i.height += 1
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                        }
//                        if (findTile(position.getNewWithSetAxis(-1,newX))?.tile == tile) {
//                            val other = findTile(position.getNewWithSetAxis(-1,newX))
//                            if (other is TileColumn && other.height == height && other.axis == newZ &&
//                                other.location.getNewWithSetAxis(1,newX) == position) {
//                                toRemove.add(i)
//                                if (newZ == Y) { addPlane(other.location,2,height,newY,tile,registry) }
//                                else { addPlane(other.location,height,2,newY,tile,registry) }
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                            if (other is TilePlane && other.axis == newY && other.location.getAxis(newZ) == position.getAxis(newZ) && other.getAxis(newZ) == height) {
//                                toRemove.add(i)
//                                complete = false
//                                other.alterAxis(other.getAxis(newX) + 1, newX)
//                                break
//                            }
//                        }
//                        if (findTile(position.getNewWithSetAxis(1,newX))?.tile == tile) {
//                            val other = findTile(position.getNewWithSetAxis(1,newX))
//                            if (other is TileColumn && other.height == height && other.axis == newZ) {
//                                if (other.location.getNewWithSetAxis(1,newX) == position) {
//                                    toRemove.add(i)
//                                    if (newZ == Y) { addPlane(position,2,height,newY,tile,registry) }
//                                    else { addPlane(position,height,2,newY,tile,registry) }
//                                    remove(other)
//                                    complete = false
//                                    break
//                                }
//                            }
//                            if (other is TilePlane && other.axis == newY && other.location.getAxis(newZ) == position.getAxis(newZ) && other.getAxis(newZ) == height) {
//                                toRemove.add(i)
//                                complete = false
//                                other.alterAxis(other.getAxis(newX) + 1, newX)
//                                other.location.setAxis(other.location.getAxis(newX) - 1, newX)
//                                break
//                            }
//                        }
//                        if (findTile(position.getNewWithSetAxis(-1,newY))?.tile == tile) {
//                            val other = findTile(position.getNewWithSetAxis(-1,newY))
//                            if (other is TileColumn && other.height == height && other.axis == newZ &&
//                                other.location.getNewWithSetAxis(1,newY) == position) {
//                                toRemove.add(i)
//                                if (newZ == Y) { addPlane(other.location,2,height,newX,tile,registry) }
//                                else { addPlane(other.location,height,2,newX,tile,registry) }
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                            if (other is TilePlane && other.axis == newX && other.location.getAxis(newZ) == position.getAxis(newZ) && other.getAxis(newZ) == height) {
//                                toRemove.add(i)
//                                complete = false
//                                other.alterAxis(other.getAxis(newY) + 1, newY)
//                                break
//                            }
//                        }
//                        if (findTile(position.getNewWithSetAxis(1,newY))?.tile == tile) {
//                            val other = findTile(position.getNewWithSetAxis(1,newY))
//                            if (other is TileColumn && other.height == height && other.axis == newZ &&
//                                other.location.getNewWithSetAxis(1,newY) == position) {
//                                toRemove.add(i)
//                                if (newZ == Y) { addPlane(position,2,height,newX,tile,registry) }
//                                else { addPlane(position,height,2,newX,tile,registry) }
//                                remove(other)
//                                complete = false
//                                break
//                            }
//                            if (other is TilePlane && other.axis == newX && other.location.getAxis(newZ) == position.getAxis(newZ) && other.getAxis(newZ) == height) {
//                                toRemove.add(i)
//                                complete = false
//                                other.alterAxis(other.getAxis(newY) + 1, newY)
//                                other.location.setAxis(other.location.getAxis(newY) - 1, newY)
//                                break
//                            }
//                        }
//                    }
//                }
//            }
//            for (i in toRemove) { remove(i) }
//            toRemove.clear()
//            if (complete) { return }
//        }
    }
    private fun getHeight(xin : Number, yin : Number, yoff : Number = 0) : Double {
        val offset = yoff.toInt() + Y_OFFSET - location.z * CELL_SIZE
        return (SimplexNoise.noise((xin.toDouble() + location.x * CELL_SIZE) / H_SCALE, (yin.toDouble() + location.y * CELL_SIZE) / H_SCALE ) / V_SCALE * CELL_SIZE + offset )
    }
    private fun addPlane(location: IntVector3, height: Int, width: Int, axis: Axis, tile: TileInstance, registry: ServerRegistry) {
//        if (location.x >= 0 && location.y >= 0 && location.z >= 0 && location.x < CELL_SIZE && location.y < CELL_SIZE && location.z < CELL_SIZE) {
//            if (tile.getTile(registry) is TickableTile) {
//                tickableTileMap.add(TilePlane(location, height, width, axis, tile))
//            } else {
//                tileMap.add(TilePlane(location, height, width, axis, tile))
//            }
//        }
    }
    private fun addColumn(location: IntVector3, height: Int, axis: Axis, tile: TileInstance, registry: ServerRegistry) {
        if (height <= 0) return
        if (location.getAxis(axis) < 0) {
            if (location.getAxis(axis) + height > 0) {
//                tilemap.add(TileColumn(location.getNewWithSetAxis(-location.getAxis(axis),axis),height - location.getAxis(axis),axis,tile))
                return
            }
        } else if (location.getAxis(axis) < CELL_SIZE) {
            val column = TileColumn(location,height,axis,tile)
            tileMap[location] = column
            return
        }
    }
    private fun addUnit(location: IntVector3, tile: TileInstance, registry: ServerRegistry) {
        if (location.x >= 0 && location.y >= 0 && location.z >= 0 && location.x < CELL_SIZE && location.y < CELL_SIZE && location.z < CELL_SIZE) {
            tileMap[location] = TileUnit(location, tile)
        }
    }

    // Companions
    companion object Global {
        const val CELL_SIZE = 32
        const val H_SCALE : Double = 50.0
        const val V_SCALE : Double = 5.0
        const val Y_OFFSET : Int = 0
        const val SOIL_DEPTH : Int = 3
    }
}
