package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.data.*
import org.nullversionnova.data.Direction.*
import org.nullversionnova.data.Axis.*
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
    var direction = NORTH
    var depth = 0

    // Methods
    private fun getCellLayer(cellCoordinates: IntegerVector3, cells: MutableMap<IntegerVector3,WorldCell>, depth: Int): MutableSet<Tile> {
        val layer = mutableSetOf<Tile>()
        if (cells[cellCoordinates] == null) {
            return layer
        }
        for (i in cells[cellCoordinates]!!.tilemap) {
            when (direction.axis()) {
                X -> if (i.location.x == depth) { layer.add(i) }
                Y -> if (i.location.y == depth) { layer.add(i) }
                Z -> if (i.location.z == depth) { layer.add(i) }
            }
        }
        if (!direction.polarity()) {
            for (i in cells[cellCoordinates]?.tilemap!!) {
                when (direction.axis()) {
                    X -> i.location.x = WorldCell.CELL_SIZE_X - i.location.x
                    Y -> i.location.y = WorldCell.CELL_SIZE_Y - i.location.y
                    Z -> i.location.z = WorldCell.CELL_SIZE_Z - i.location.z
                }
            }
        }
        return layer
    }
    private fun getTileLayer(layer: MutableSet<Tile>, map: TiledMap) : TiledMapTileLayer {
        val tileLayer : TiledMapTileLayer = when(direction.axis()) {
            X -> TiledMapTileLayer(WorldCell.CELL_SIZE_Y,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            Y -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Z, Client.scale, Client.scale)
            Z -> TiledMapTileLayer(WorldCell.CELL_SIZE_X,WorldCell.CELL_SIZE_Y, Client.scale, Client.scale)
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
            allTiles[i] = Cell().setTile(textureIds[getTileTexture(direction,i)]?.let { map.tileSets.getTile(it) })
        }
        for (i in layer) {
            val x = when (direction.axis()) {
                X -> i.location.y
                Y -> i.location.x
                Z -> i.location.x
            }
            val y = when (direction.axis()) {
                X -> i.location.z
                Y -> i.location.z
                Z -> i.location.y
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
            val newCameraPosition = when (direction.axis()) {
                X -> IntegerVector3(cameraCellCoordinates.x + displacement,cameraCellCoordinates.y,cameraCellCoordinates.z)
                Y -> IntegerVector3(cameraCellCoordinates.x, cameraCellCoordinates.y + displacement,cameraCellCoordinates.z)
                Z -> IntegerVector3(cameraCellCoordinates.x,cameraCellCoordinates.y,cameraCellCoordinates.z + displacement)
            }
            map.layers.add(getTileLayer(getCellLayer(newCameraPosition, cells, depthDirection(depth,direction,i)),map))
        }
        println("Reloaded!")
        return map
    }

    // Companions
    companion object Global {
        const val renderDistance = 32
        fun depthDirection(depth: Int, direction: Direction, increase: Int) : Int {
            return depth + if (direction.polarity()) {
                increase
            } else {
                -increase
            }
        }
    }
}
