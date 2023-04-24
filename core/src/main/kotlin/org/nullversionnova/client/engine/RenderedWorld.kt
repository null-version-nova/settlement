package org.nullversionnova.client.engine

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import kotlinx.coroutines.runBlocking
import org.nullversionnova.client.engine.Client.Global.getTileTexture
import org.nullversionnova.common.Direction
import org.nullversionnova.common.Direction.*
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector2
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.cell.WorldCell
import org.nullversionnova.server.engine.tiles.TileInstance

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
    var cameraCellCoordinates = IntVector3()
    var direction = NORTH
    var depth = 0
    var full = false

    // Methods
    private fun getCellLayer(cellCoordinates: IntVector3, cells: MutableMap<IntVector3, WorldCell>, depth: Int): MutableSet<TileInstance> {
        val displacement = depth % WorldCell.CELL_SIZE
        val layer = mutableSetOf<TileInstance>()
        if (cells[cellCoordinates] == null) {
            return layer
        }
        val scan = IntVector3()
        scan.setAxis(displacement,direction.axis())
        for (i in 0 until WorldCell.CELL_SIZE) {
            for (j in 0 until WorldCell.CELL_SIZE) {
                val landing = scan.getNewWithSetAxis(i,direction.axis().getOtherPair().first).getNewWithSetAxis(j,direction.axis().getOtherPair().second)
                val result = cells[cellCoordinates]!!.findTile(landing)
                if (result != null) {
                    layer.add(result)
                }
            }
        }
//        for (i in cells[cellCoordinates]!!.tileMap) {
//            if (i is TileColumn) {
//                for (j in i.split()) {
//                    if (j.location.getAxis(direction.axis()) == displacement) {
//                        layer.add(j)
//                    }
//                }
//            }
//            if (i is TileUnit) {
//                if (i.location.getAxis(direction.axis()) == displacement) {
//                    layer.add(i)
//                }
//            }
//        }
        return layer
    }
    private fun getTileLayer(map: TiledMap, layers : MutableMap<IntVector2,MutableSet<TileInstance>>) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.CELL_SIZE * 3, WorldCell.CELL_SIZE * 3, Client.scale, Client.scale)
        val allTiles = mutableMapOf<Identifier,Cell>()
        val allTileKeys = mutableSetOf<Identifier>()
        for (i in layers) { // What a lifesaver!
            for (j in i.value) {
                allTileKeys.add(j.identifier)
            }
        }
        for (i in allTileKeys) {
            allTiles[i] = Cell().setTile(textureIds[getTileTexture(direction,i)]?.let { map.tileSets.getTile(it) })
        }
        for (i in layers.keys) {
            for (j in layers[i]!!) {
                val vector = IntVector2(cameraCellCoordinates,direction.axis())
                var x = when (direction) {
                    NORTH, SOUTH -> j.location!!.x + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                    EAST, WEST -> j.location!!.y + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                    UP, DOWN -> j.location!!.x + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                }
                var y = when (direction) {
                    NORTH, SOUTH, EAST, WEST -> j.location!!.z + i.y * WorldCell.CELL_SIZE + -vector.y * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE
                    DOWN, UP -> j.location!!.y + i.y * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.y
                }
                when (direction) {
                    SOUTH -> x = WorldCell.CELL_SIZE * 3 - x
                    WEST -> x = WorldCell.CELL_SIZE * 3 - x
                    UP -> y = WorldCell.CELL_SIZE * 3 - y
                    else -> {}
                }
                tileLayer.setCell(x,y, allTiles[j.identifier])
            }
        }
        allTiles.clear()
        allTileKeys.clear()
        layers.clear()
        return tileLayer
    }
    private fun adjustTileLayer(oldLayer: TiledMapTileLayer, newCameraPosition: IntVector3, loadedCells: MutableSet<IntVector3>, cells: MutableMap<IntVector3, WorldCell>, depth: Int) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.CELL_SIZE * 3, WorldCell.CELL_SIZE * 3, Client.scale, Client.scale)
        val layerSize = WorldCell.CELL_SIZE * 3 - 1
        for (i in 0 until layerSize) {
            for (j in 0 until layerSize) {

            }
        }
        return tileLayer
    }
    private fun getLayers(loadedCells: MutableSet<IntVector3>, cells: MutableMap<IntVector3, WorldCell>, depth: Int) : MutableMap<IntVector2,MutableSet<TileInstance>> {
        val displacement = if (depth >= WorldCell.CELL_SIZE) { 1 } else if (depth < 0) { -1 } else { 0 }
        val layers = mutableMapOf<IntVector2,MutableSet<TileInstance>>()
        for (i in loadedCells) {
            if (i.getAxis(direction.axis()) == cameraCellCoordinates.getAxis(direction.axis()) + displacement) {
                layers[IntVector2(i,direction.axis())] = getCellLayer(i, cells, depth - displacement * WorldCell.CELL_SIZE)
            }
        }
        return layers
    }
    fun reloadMap(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>, oldMap: TiledMap? = null) : TiledMap {
        val map = TiledMap()
        val layers = mutableListOf<MutableMap<IntVector2,MutableSet<TileInstance>>>()
        oldMap?.dispose()
        map.tileSets.addTileSet(tileSet)
        for (i in 0..renderDistance) {
            val layer = if (full) {
                mutableMapOf()
            } else {
                getLayers(loadedCells, cells, depthDirection(depth, direction, i))
            }
            layers.add(layer)
//            var count = 0
//            for (j in layer.values) {
//                count += j.size
//            }
//            if (count == 9216) {
//                full = true
//            }
        }
        for (i in renderDistance downTo 0) {
            map.layers.add(getTileLayer(map,layers[i]))
        }
        full = false
        return map
    }
    fun advanceDepth(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>, oldMap: TiledMap) : TiledMap {
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        runBlocking { map.layers.add(getTileLayer(map, getLayers(loadedCells, cells, depthDirection(depth, direction, renderDistance)))) }
        for (i in 0 until renderDistance - 1) {
            map.layers.add(oldMap.layers[i])
        }
        oldMap.dispose()
        return map
    }
    fun recedeDepth(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>, oldMap: TiledMap) : TiledMap {
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 1 until renderDistance) {
            map.layers.add(oldMap.layers[i])
        }
        runBlocking { map.layers.add(getTileLayer(map, getLayers(loadedCells, cells, depth))) }
        oldMap.dispose()
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
