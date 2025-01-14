package org.nullversionnova.client

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import kotlinx.coroutines.runBlocking
import org.nullversionnova.client.Client.Companion.getTileTexture
import org.nullversionnova.math.Direction3
import org.nullversionnova.math.Direction3.*
import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.Server
import org.nullversionnova.world.WorldCell
import org.nullversionnova.world.tiles.TileInstance

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
    private val cullingMemory = Array(WorldCell.CELL_SIZE) { _ -> Array(WorldCell.CELL_SIZE) { _ -> false } }
    var direction = DOWN
    var depth = 128

    // Methods
    private fun getTileLayer(map: TiledMap, layers : MutableSet<TileInstance>) : TiledMapTileLayer {
        val tileLayer = TiledMapTileLayer(WorldCell.CELL_SIZE, WorldCell.CELL_SIZE, Client.SCALE, Client.SCALE)
        val allTiles = mutableMapOf<Identifier,Cell>()
        for (i in layers) {
            val vector = i.location
            val x = vector.x
            val y = vector.y
            if (!allTiles.keys.contains(i.getTexture())) {
                allTiles[i.getTexture()] = Cell().setTile(textureIds[getTileTexture(direction,i.getTexture())]?.let { map.tileSets.getTile(it) })
            }
            tileLayer.setCell(x,y, allTiles[i.identifier])
        }
        allTiles.clear()
        return tileLayer
    }
    private fun getLayer(server: Server, depth: Int) : MutableSet<TileInstance> {
        val layers = mutableSetOf<TileInstance>()
        for (i in 0 until WorldCell.CELL_SIZE) {
            for (j in 0 until WorldCell.CELL_SIZE) {
                server.loadedCell[IntVector3(i,j,depth)]?.let { layers.add(it) }
            }
        }
        return layers
    }
    private fun layerCast(server: Server, depth: Int) : MutableSet<TileInstance> {
        val layers = mutableSetOf<TileInstance>()
        if (depth < 0) {
            return layers
        }
        for (i in 0 until WorldCell.CELL_SIZE) {
            for (j in 0 until WorldCell.CELL_SIZE) {
                if (!cullingMemory[i][j]) {
                    server.loadedCell[IntVector3(i,j,depth)]?.let {
                        layers.add(it)
                        cullingMemory[i][j] = true
                    }
                }
            }
        }
        return layers
    }
    fun resetMap(server: Server, oldMap: TiledMap? = null) : TiledMap {
        val map = TiledMap()
        oldMap?.dispose()
        map.tileSets.addTileSet(tileSet)
        map.layers.add(getTileLayer(map,getLayer(server,depth)))
        return map
    }
    fun renderMore(server: Server, oldMap: TiledMap? = null) : TiledMap {
        if (oldMap == null) { resetMap(server) }
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        runBlocking { map.layers.add(getTileLayer(map, getLayer(server, depthDirection(depth, direction, oldMap!!.layers.count)))) }
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
                map.layers.add(getTileLayer(map,getLayer(server, depthDirection(this.depth, direction, depth))))
            } else {
                map.layers.add(oldMap.layers[i])
            }
        }
        oldMap.dispose()
        return map
    }
    fun advanceDepth(oldMap: TiledMap) : TiledMap {
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
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
        resetCull()
        runBlocking { map.layers.add(getTileLayer(map, layerCast(server, depth))) }
        oldMap.dispose()
        return map
    }
    fun renderCast(server: Server, oldMap: TiledMap? = null) : TiledMap {
        val map = TiledMap()
        oldMap?.dispose()
        map.tileSets.addTileSet(tileSet)
        for (i in 0 until renderDistance) {
            map.layers.add(getTileLayer(map,layerCast(server,depth - i)))
        }
        resetCull()
        return map
    }
    fun renderCastOver(depth: Int, server: Server, oldMap: TiledMap? = null) : TiledMap {
        if (oldMap == null) { resetMap(server) }
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in 0 until oldMap!!.layers.count) {
            if (oldMap.layers.count - i - 1 == depth) {
                map.layers.add(getTileLayer(map,layerCast(server, depthDirection(this.depth, direction, depth))))
            } else {
                map.layers.add(oldMap.layers[i])
            }
        }
        oldMap.dispose()
        return map
    }
    fun renderCastMore(server: Server, oldMap: TiledMap? = null) : TiledMap {
        if (oldMap == null) { resetMap(server) }
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        runBlocking { map.layers.add(getTileLayer(map, layerCast(server, depthDirection(depth, direction, oldMap!!.layers.count)))) }
        for (i in 0 until oldMap!!.layers.count) {
            map.layers.add(oldMap.layers[i])
        }
        oldMap.dispose()
        if (map.layers.count == renderDistance) {
            resetCull()
        }
        return map
    }
    fun resetCull() {
        for (i in cullingMemory) {
            i.fill(false)
        }
    }

    // Companions
    companion object {
        const val renderDistance = 256
        fun depthDirection(depth: Int, direction: Direction3, increase: Int) : Int {
            return depth + if (direction.polarity()) {
                increase
            } else {
                -increase
            }
        }
    }
}
