package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector2
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.data.TileGroup2
import org.nullversionnova.data.TileGroup3
import org.nullversionnova.data.TileGroups2
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
    var cameraCellCoordinates = IntegerVector3(0,0,0)
    var direction = 0
    var depth = 0

    private fun getCellLayer(cellCoordinates: IntegerVector3, cells: MutableMap<IntegerVector3,WorldCell>, direction: Int, depth: Int): TileGroups2 {
        val layer = mutableListOf<TileGroup2>()
        val preLayer = mutableListOf<TileGroup3>()
        val cell = cells[cellCoordinates] ?: return TileGroups2(layer)
        preLayer.addAll(cell.tilemap.findAllInPlane(affectedAxis(direction), depth))
        for (i in preLayer) { layer.add(i.slice(affectedAxis(direction))) }
        return if (direction % 2 == 0) {
            TileGroups2(layer)
        } else {
            TileGroups2(layer).reflect(64)
        }
    }
    private fun getTileLayer(layer: TileGroups2, map: TiledMap, axis : Int) : TiledMapTileLayer {
        val tileLayer : TiledMapTileLayer = when(axis) {
            0 -> TiledMapTileLayer(WorldCell.CELL_SIZE_Y,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            1 -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            else -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Y, Client.scale, Client.scale)
        }
        val allTiles = mutableMapOf<Identifier,Cell>()
        for (i in layer.listAllTilesInGroup()) { // What a lifesaver!
            allTiles[i] = Cell().setTile(textureIds[getTileTexture(axis,i)]?.let { map.tileSets.getTile(it) })
        }
        for (i in 0 until tileLayer.width) {
            for (j in 0 until tileLayer.height) {
                tileLayer.setCell(i,j, allTiles[layer.findTileGroup(IntegerVector2(i,j))?.identifier])
            }
        }
        return tileLayer
    }
    fun reloadMap(cells: MutableMap<IntegerVector3, WorldCell>) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 0..renderDistance) {
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
        const val renderDistance = 16
        fun depthDirection(depth: Int, direction: Int, increase: Int) : Int {
            return depth + if (direction % 2 == 0) {
                increase
            } else { // wretched code. i hate this
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
