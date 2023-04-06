package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.data.Tile
import org.nullversionnova.server.WorldCell
import kotlin.math.floor

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
    var cameraCellCoordinates = IntegerVector3()
    var direction = 0
    var depth = 0

    private fun getCellLayer(cellCoordinates: IntegerVector3, cells: MutableMap<IntegerVector3,WorldCell>, direction: Int, depth: Int): MutableSet<Tile> {
        val layer = mutableSetOf<Tile>()
        if (cells[cellCoordinates] == null) {
            return layer
        }
        for (i in cells[cellCoordinates]!!.tilemap) {
            when (affectedAxis(direction)) {
                0 -> if (i.location.x == depth) { layer.add(i) }
                1 -> if (i.location.y == depth) { layer.add(i) }
                2 -> if (i.location.z == depth) { layer.add(i) }
            }
        }
        if (direction % 2 != 0) {
            for (i in cells[cellCoordinates]?.tilemap!!) {
                when (affectedAxis(direction)) {
                    0 -> i.location.x = WorldCell.CELL_SIZE_X - i.location.x
                    1 -> i.location.y = WorldCell.CELL_SIZE_Y - i.location.y
                    2 -> i.location.z = WorldCell.CELL_SIZE_Z - i.location.z
                }
            }
        }
        return layer
    }
    private fun getTileLayer(layer: MutableSet<Tile>, map: TiledMap, axis : Int) : TiledMapTileLayer {
        val tileLayer : TiledMapTileLayer = when(axis) {
            0 -> TiledMapTileLayer(WorldCell.CELL_SIZE_Y,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            1 -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            else -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Y, Client.scale, Client.scale)
        }
        if (layer.isEmpty()) {
            return tileLayer
        }
        val allTiles = mutableMapOf<Identifier,Cell>()
        val allTileKeys = mutableSetOf<Identifier>()
        for (i in layer) { // What a lifesaver!
            allTileKeys.add(i.identifier)
        }
        for (i in allTileKeys) {
            allTiles[i] = Cell().setTile(textureIds[getTileTexture(axis,i)]?.let { map.tileSets.getTile(it) })
        }
        for (i in layer) {
            val x = when (axis) {
                0 -> i.location.y
                1 -> i.location.x
                else -> i.location.x
            }
            val y = when (axis) {
                0 -> i.location.z
                1 -> i.location.z
                else -> i.location.y
            }
            tileLayer.setCell(x,y, allTiles[i.identifier])
        }
        return tileLayer
    }
    fun reloadMap(cells: MutableMap<IntegerVector3, WorldCell>) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in renderDistance downTo 0) {
            val displacement = floor(depthDirection(depth,direction,i).toFloat() / WorldCell.CELL_SIZE_X.toFloat()).toInt()
            val newCameraPosition = when (affectedAxis(direction)) {
                0 -> IntegerVector3(cameraCellCoordinates.x + displacement,cameraCellCoordinates.y,cameraCellCoordinates.z)
                1 -> IntegerVector3(cameraCellCoordinates.x, cameraCellCoordinates.y + displacement,cameraCellCoordinates.z)
                else -> IntegerVector3(cameraCellCoordinates.x,cameraCellCoordinates.y,cameraCellCoordinates.z + displacement)
            }
            map.layers.add(getTileLayer(getCellLayer(newCameraPosition, cells, direction, depthDirection(depth,direction,i)),map, affectedAxis(direction)))
        }
        println("Reloaded!")
        return map
    }

    companion object Global {
        const val renderDistance = 32
        fun depthDirection(depth: Int, direction: Int, increase: Int) : Int {
            return depth + if (direction % 2 == 0) {
                increase
            } else {
                -increase
            }
        }
        fun affectedAxis(direction: Int) : Int {
            return when (direction) {
                0, 1 -> { 0 }
                2, 3 -> { 1 }
                else -> { 2 }
            }
        }
    }
}
