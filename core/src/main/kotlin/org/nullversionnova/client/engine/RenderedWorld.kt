package org.nullversionnova.client.engine

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import kotlinx.coroutines.runBlocking
import org.nullversionnova.client.engine.Client.Companion.getTileTexture
import org.nullversionnova.common.Direction
import org.nullversionnova.common.Direction.*
import org.nullversionnova.common.Global.convertPositionToGlobal
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.Server
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

    // Methods
    private fun getTileLayer(map: TiledMap, layers : MutableSet<TileInstance>) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.CELL_SIZE * 3, WorldCell.CELL_SIZE * 3, Client.scale, Client.scale)
        val allTiles = mutableMapOf<Identifier,Cell>()
        for (i in layers) {
            val vector = i.location!!
            val camera = convertPositionToGlobal(cameraCellCoordinates)
            var x = when (direction) {
                NORTH, SOUTH, UP, DOWN -> vector.x - camera.x + WorldCell.CELL_SIZE
                EAST, WEST -> vector.y - camera.y + WorldCell.CELL_SIZE
            }
            var y = when (direction) {
                NORTH, SOUTH, EAST, WEST -> vector.z - camera.z + WorldCell.CELL_SIZE
                DOWN, UP -> vector.y - camera.y + WorldCell.CELL_SIZE
            }
            when (direction) {
                SOUTH -> x = WorldCell.CELL_SIZE * 3 - x
                WEST -> x = WorldCell.CELL_SIZE * 3 - x
                UP -> y = WorldCell.CELL_SIZE * 3 - y
                else -> {}
            }
            if (!allTiles.keys.contains(i.getTexture())) {
                allTiles[i.getTexture()] = Cell().setTile(textureIds[getTileTexture(direction,i.getTexture())]?.let { map.tileSets.getTile(it) })
            }
            tileLayer.setCell(x,y, allTiles[i.identifier])
        }
        allTiles.clear()
        return tileLayer
    }
    private fun getLayers(server: Server, depth: Int) : MutableSet<TileInstance> {
        val layers = mutableSetOf<TileInstance>()
        val scan = convertPositionToGlobal(cameraCellCoordinates).getNewWithSetAxis(depth,direction.axis())
        for (i in -WorldCell.CELL_SIZE until WorldCell.CELL_SIZE * 2) {
            for (j in -WorldCell.CELL_SIZE until WorldCell.CELL_SIZE * 2) {
                val landing = scan.getNewWithSetAxis(i,direction.axis().getOtherPair().first).getNewWithSetAxis(j,direction.axis().getOtherPair().second)
                server[landing]?.let { layers.add(it) }
            }
        }
        return layers
    }
    fun reloadMap(server: Server, oldMap: TiledMap? = null) : TiledMap {
        println(cameraCellCoordinates)
        val map = TiledMap()
        oldMap?.dispose()
        map.tileSets.addTileSet(tileSet)
        for (i in renderDistance downTo 0) {
            map.layers.add(getTileLayer(map,getLayers(server, depthDirection(depth, direction, i))))
        }
        return map
    }
    fun resetMap(server: Server, oldMap: TiledMap? = null) : TiledMap {
        val map = TiledMap()
        oldMap?.dispose()
        map.tileSets.addTileSet(tileSet)
        map.layers.add(getTileLayer(map,getLayers(server,depth)))
        return map
    }
    fun renderMore(server: Server, oldMap: TiledMap? = null) : TiledMap {
        if (oldMap == null) { resetMap(server) }
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        runBlocking { map.layers.add(getTileLayer(map, getLayers(server, depthDirection(depth, direction, oldMap!!.layers.count)))) }
        for (i in 0 until oldMap!!.layers.count) {
            map.layers.add(oldMap.layers[i])
        }
        oldMap.dispose()
        return map
    }
    fun renderOver(depth: Int, server: Server, oldMap: TiledMap? = null) : TiledMap {
        if (oldMap == null) { resetMap(server) }
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 0 until oldMap!!.layers.count) {
            if (oldMap.layers.count - i - 1 == depth) {
                map.layers.add(getTileLayer(map,getLayers(server, depthDirection(this.depth, direction, depth))))
            } else {
                map.layers.add(oldMap.layers[i])
            }
        }
        oldMap.dispose()
        return map
    }
    fun advanceDepth(server: Server, oldMap: TiledMap) : TiledMap {
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        runBlocking { map.layers.add(getTileLayer(map, getLayers(server, depthDirection(depth, direction, oldMap.layers.count)))) }
        for (i in 0 until oldMap.layers.count - 1) {
            map.layers.add(oldMap.layers[i])
        }
        oldMap.dispose()
        return map
    }
    fun recedeDepth(server: Server, oldMap: TiledMap) : TiledMap {
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 1 until oldMap.layers.count) {
            map.layers.add(oldMap.layers[i])
        }
        runBlocking { map.layers.add(getTileLayer(map, getLayers(server, depth))) }
        oldMap.dispose()
        return map
    }

    // Companions
    companion object {
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
