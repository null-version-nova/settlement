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
        val displacement = depth % WorldCell.CELL_SIZE_X
        val layer = mutableSetOf<Tile>()
        if (cells[cellCoordinates] == null) {
            return layer
        }
        for (i in cells[cellCoordinates]!!.tilemap) {
            when (direction.axis()) {
                X -> if (i.location.x == displacement) { layer.add(i) }
                Y -> if (i.location.y == displacement) { layer.add(i) }
                Z -> if (i.location.z == displacement) { layer.add(i) }
            }
        }
        return layer
    }
    private fun getTileLayer(loadedCells: MutableSet<IntegerVector3>, cells: MutableMap<IntegerVector3, WorldCell>, depth: Int, map: TiledMap) : TiledMapTileLayer {
        val tileLayer : TiledMapTileLayer = when(direction.axis()) {
            X -> TiledMapTileLayer(WorldCell.CELL_SIZE_Y * 3,WorldCell.CELL_SIZE_Z * 3, Client.scale, Client.scale)
            Y -> TiledMapTileLayer(WorldCell.CELL_SIZE_X * 3,WorldCell.CELL_SIZE_Z * 3, Client.scale, Client.scale)
            Z -> TiledMapTileLayer(WorldCell.CELL_SIZE_X * 3,WorldCell.CELL_SIZE_Y * 3, Client.scale, Client.scale)
        }
        val layers = mutableMapOf<IntegerVector2,MutableSet<Tile>>()
        val displacement = if (depth > WorldCell.getSizeFromAxis(direction.axis())) { 1 } else if (depth < 0) { -1 } else { 0 }
        for (i in loadedCells) {
            if (i.getAxis(direction.axis()) == cameraCellCoordinates.getAxis(direction.axis()) + displacement) {
                if (displacement == 1) {
                }
                layers[IntegerVector2(i,direction.axis())] = getCellLayer(i.getNewWithSetAxis(displacement,direction.axis()), cells, depth - displacement * WorldCell.getSizeFromAxis(direction.axis()))
            }
        }
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
                val vector = IntegerVector2(cameraCellCoordinates,direction.axis())
                var x = when (direction) {
                    NORTH, SOUTH -> j.location.x + i.x * WorldCell.CELL_SIZE_Y + WorldCell.CELL_SIZE_Y + WorldCell.CELL_SIZE_Y * -vector.x
                    EAST, WEST -> j.location.y + i.x * WorldCell.CELL_SIZE_X + WorldCell.CELL_SIZE_X + WorldCell.CELL_SIZE_X * -vector.x
                    UP, DOWN -> j.location.x + i.x * WorldCell.CELL_SIZE_X + WorldCell.CELL_SIZE_X + WorldCell.CELL_SIZE_X * -vector.x
                }
                var y = when (direction) {
                    NORTH, SOUTH, EAST, WEST -> j.location.z + i.y * WorldCell.CELL_SIZE_Z + -vector.y * WorldCell.CELL_SIZE_Z + WorldCell.CELL_SIZE_Z
                    DOWN, UP -> j.location.y + i.y * WorldCell.CELL_SIZE_Y + WorldCell.CELL_SIZE_Y + WorldCell.CELL_SIZE_Y * -vector.y
                }
                when (direction) {
                    SOUTH -> x = WorldCell.CELL_SIZE_Y * 3 - x
                    WEST -> x = WorldCell.CELL_SIZE_X * 3 - x
                    UP -> y = WorldCell.CELL_SIZE_Y * 3 - y
                    else -> {}
                }
                tileLayer.setCell(x,y, allTiles[j.identifier])
            }
        }
        return tileLayer
    }
    fun reloadMap(cells: MutableMap<IntegerVector3, WorldCell>, loadedCells: MutableSet<IntegerVector3>) : TiledMap {
        println("Beginning reload.")
        val map = TiledMap()
        map.tileSets.addTileSet(tileSet)
        for (i in renderDistance downTo 0) {
            map.layers.add(getTileLayer(loadedCells,cells,depthDirection(depth,direction,i),map))
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
