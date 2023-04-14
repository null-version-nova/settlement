package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import org.nullversionnova.client.Client.Global.getTileTexture
import org.nullversionnova.common.*
import org.nullversionnova.common.Direction.*
import org.nullversionnova.server.WorldCell
import org.nullversionnova.server.engine.tiles.TileColumn
import org.nullversionnova.server.engine.tiles.TileUnit

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

    // Methods
    private fun getCellLayer(cellCoordinates: IntVector3, cells: MutableMap<IntVector3,WorldCell>, depth: Int): MutableSet<TileUnit> {
        val displacement = depth % WorldCell.CELL_SIZE
        val layer = mutableSetOf<TileUnit>()
        if (cells[cellCoordinates] == null) {
            return layer
        }

        for (i in cells[cellCoordinates]!!.tilemap) {
            if (i is TileColumn) {
                for (j in i.split()) {
                    if (j.location.getAxis(direction.axis()) == displacement) {
                        layer.add(j)
                    }
                }
            }
            if (i is TileUnit) {
                if (i.location.getAxis(direction.axis()) == displacement) {
                    layer.add(i)
                }
            }
        }
        return layer
    }
    private fun getTileLayer(loadedCells: MutableSet<IntVector3>, cells: MutableMap<IntVector3, WorldCell>, depth: Int, map: TiledMap) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.CELL_SIZE * 3,WorldCell.CELL_SIZE * 3, Client.scale, Client.scale)
        val layers = mutableMapOf<IntVector2,MutableSet<TileUnit>>()
        val displacement = if (depth >= WorldCell.CELL_SIZE) { 1 } else if (depth < 0) { -1 } else { 0 }
        for (i in loadedCells) {
            if (i.getAxis(direction.axis()) == cameraCellCoordinates.getAxis(direction.axis()) + displacement) {
                layers[IntVector2(i,direction.axis())] = getCellLayer(i, cells, depth - displacement * WorldCell.CELL_SIZE)
            }
        }
        val allTiles = mutableMapOf<Identifier,Cell>()
        val allTileKeys = mutableSetOf<Identifier>()
        for (i in layers) { // What a lifesaver!
            for (j in i.value) {
                allTileKeys.add(j.tile.identifier)
            }
        }
        for (i in allTileKeys) {
            allTiles[i] = Cell().setTile(textureIds[getTileTexture(direction,i)]?.let { map.tileSets.getTile(it) })
        }
        for (i in layers.keys) {
            for (j in layers[i]!!) {
                val vector = IntVector2(cameraCellCoordinates,direction.axis())
                var x = when (direction) {
                    NORTH, SOUTH -> j.location.x + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                    EAST, WEST -> j.location.y + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                    UP, DOWN -> j.location.x + i.x * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.x
                }
                var y = when (direction) {
                    NORTH, SOUTH, EAST, WEST -> j.location.z + i.y * WorldCell.CELL_SIZE + -vector.y * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE
                    DOWN, UP -> j.location.y + i.y * WorldCell.CELL_SIZE + WorldCell.CELL_SIZE + WorldCell.CELL_SIZE * -vector.y
                }
                when (direction) {
                    SOUTH -> x = WorldCell.CELL_SIZE * 3 - x
                    WEST -> x = WorldCell.CELL_SIZE * 3 - x
                    UP -> y = WorldCell.CELL_SIZE * 3 - y
                    else -> {}
                }
                tileLayer.setCell(x,y, allTiles[j.tile.identifier])
            }
        }
        allTiles.clear()
        allTileKeys.clear()
        layers.clear()
        return tileLayer
    }

    fun reloadMap(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in renderDistance downTo 0) {
            map.layers.add(getTileLayer(loadedCells, cells, depthDirection(depth, direction, i), map))
        }
        println("Reloaded!")
        return map
    }
    fun advanceDepth(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>, oldMap: TiledMap) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        map.layers.add(getTileLayer(loadedCells, cells, depthDirection(depth, direction, renderDistance), map))
        for (i in 0 until renderDistance - 1) {
            map.layers.add(oldMap.layers[i])
        }
        println("Reloaded!")
        oldMap.dispose()
        return map
    }
    fun recedeDepth(cells: MutableMap<IntVector3, WorldCell>, loadedCells: MutableSet<IntVector3>, oldMap: TiledMap) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 1 until renderDistance) {
            map.layers.add(oldMap.layers[i])
        }
        map.layers.add(getTileLayer(loadedCells, cells, depth, map))
        println("Reloaded!")
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
