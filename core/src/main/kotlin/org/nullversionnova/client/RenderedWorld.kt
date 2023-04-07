package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.data.*
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
    var direction = Direction.NORTH
    var depth = 0

    // Methods
    private fun getCellLayer(cellCoordinates: IntegerVector3, cells: MutableMap<IntegerVector3,WorldCell>, depth: Int): Set<Tile> {
        val layer = mutableSetOf<Tile>()
        if (cells[cellCoordinates] == null) { return layer }
        for (i in cells[cellCoordinates]!!.tilemap) { if (i.location.getAxis(direction.axis()) == depth) { layer.add(i) } }
        if (!direction.polarity()) { for (i in layer) { i.location.setAxis(WorldCell.getSizeFromAxis(direction.axis()) - i.location.getAxis(direction.axis()),direction.axis()) } }
        return layer.toSet()
    }
    private fun getTileLayer(layer: Set<Tile>, map: TiledMap) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.getSizeFromAxis(direction.getOtherPair().first),WorldCell.getSizeFromAxis(direction.getOtherPair().second), Client.scale, Client.scale)
        val allTiles = mutableMapOf<Identifier,Cell>()
        val allTileKeys = mutableSetOf<Identifier>()
        if (layer.isEmpty()) { return tileLayer }
        for (i in layer) { allTileKeys.add(i.identifier) }
        for (i in allTileKeys) { allTiles[i] = Cell().setTile(textureIds[getTileTexture(direction,i)]?.let { map.tileSets.getTile(it) }) }
        for (i in layer) {
            val x = i.location.getAxis(direction.getOtherPair().first)
            val y = i.location.getAxis(direction.getOtherPair().second)
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
            val newCameraPosition = cameraCellCoordinates.getNewWithSetAxis(cameraCellCoordinates.getAxis(direction.axis()) + displacement,direction.axis())
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
