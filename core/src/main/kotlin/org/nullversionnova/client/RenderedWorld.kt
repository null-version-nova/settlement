package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.MathUtils
import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.server.WorldCell
import org.nullversionnova.server.base.Base

class RenderedWorld {
    // Initialize
    fun initialize(registry: ClientRegistry) {
        for ((counter, i) in registry.getTextureSet().withIndex()) {
            textureIds[i] = counter
            tileSet.putTile(counter, StaticTiledMapTile(TextureRegion(registry.getTexture(i))))
        }
    }

    // Members
    private val textureIds = mutableMapOf<Identifier,Int>()
    private val tileSet = TiledMapTileSet()
    var cameraCellCoordinates = IntegerVector3(0,0,0)
    var direction = 0
    var depth = 0

    private fun getCellLayer(cell: IntegerVector3, cells: MutableMap<IntegerVector3,WorldCell>, direction: Int, depth: Int): Array<Array<Identifier>> {
        lateinit var layer: Array<Array<Identifier>>
        when (direction) {
            0 -> {
                layer = Array(WorldCell.CELL_SIZE_Y) {
                    Array(WorldCell.CELL_SIZE_Z) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0 until WorldCell.CELL_SIZE_Y) {
                    for (k in 0 until WorldCell.CELL_SIZE_Z) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[depth][j][k])
                    }
                }
                return layer
            }
            1 -> {
                layer = Array(WorldCell.CELL_SIZE_Y) {
                    Array(WorldCell.CELL_SIZE_Z) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0..WorldCell.CELL_SIZE_Y) {
                    for (k in 0..WorldCell.CELL_SIZE_Z) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[depth][j][k])
                    }
                }
                return layer
            }
            2 -> {
                layer = Array(WorldCell.CELL_SIZE_X) {
                    Array(WorldCell.CELL_SIZE_Z) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0..WorldCell.CELL_SIZE_X) {
                    for (k in 0..WorldCell.CELL_SIZE_Z) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[j][depth][k])
                    }
                }
                return layer
            }
            3 -> {
                layer = Array(WorldCell.CELL_SIZE_X) {
                    Array(WorldCell.CELL_SIZE_Z) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0..WorldCell.CELL_SIZE_X) {
                    for (k in 0..WorldCell.CELL_SIZE_Z) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[j][depth][k])
                    }
                }
                return layer
            }
            4 -> {
                layer = Array(WorldCell.CELL_SIZE_Y) {
                    Array(WorldCell.CELL_SIZE_X) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0..WorldCell.CELL_SIZE_Y) {
                    for (k in 0..WorldCell.CELL_SIZE_X) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[k][j][depth])
                    }
                }
                return layer
            }
            else -> {
                layer = Array(WorldCell.CELL_SIZE_Y) {
                    Array(WorldCell.CELL_SIZE_X) {
                        Identifier(Base.pack_identifier, "air")
                    }
                }
                if (cells[cell] == null) { return layer }
                for (j in 0..WorldCell.CELL_SIZE_Y) {
                    for (k in 0..WorldCell.CELL_SIZE_X) {
                        layer[j][k] = getTileTexture(direction, cells[cell]!!.tilemap[k][j][depth])
                    }
                }
                return layer
            }
        }
    } // organize better
    private fun getTileLayer(layer: Array<Array<Identifier>>, map: TiledMap) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(layer.size,layer[0].size, Client.scale, Client.scale)
        for (i in layer.indices) {
            for (j in layer[0].indices) {
                tileLayer.setCell(i,j, TiledMapTileLayer.Cell().setTile(textureIds[layer[i][j]]?.let {
                    map.tileSets.getTile(
                        it
                    )
                }))
            }
        }
        return tileLayer
    }

    // Getters
    fun reloadMap(cells: MutableMap<IntegerVector3, WorldCell>) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 0..renderDistance) {
            val displacement = MathUtils.floor(depthDirection(depth,direction,i).toFloat() / WorldCell.CELL_SIZE_X.toFloat())
            val newCameraPosition = when (affectedAxis(direction)) {
                0 -> IntegerVector3(cameraCellCoordinates.x + displacement,cameraCellCoordinates.y,cameraCellCoordinates.z)
                1 -> IntegerVector3(cameraCellCoordinates.x, cameraCellCoordinates.y + displacement,cameraCellCoordinates.z)
                else -> IntegerVector3(cameraCellCoordinates.x,cameraCellCoordinates.y,cameraCellCoordinates.z + displacement)
            }
            map.layers.add(getTileLayer(getCellLayer(newCameraPosition, cells, direction, depthDirection(depth,direction,i)),map))
        }
        println("Reloaded!")
        return map
    }

    companion object Global {
        const val renderDistance = 8
        fun depthDirection(depth: Int, direction: Int, increase: Int) : Int {
            return depth + if (direction % 2 == 0) {
                increase
            } else { // wretched code. i hate this
                -increase
            }
        }
        fun affectedAxis(direction: Int) : Int {
            return if (direction == 0 || direction == 1) { 0 }
            else if (direction == 2 || direction == 3) { 1 }
            else { 2 }
        }
    }
}
