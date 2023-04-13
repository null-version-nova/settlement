package org.nullversionnova.client

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.beust.klaxon.Klaxon
import org.nullversionnova.client.settlement.SettlementClient
import org.nullversionnova.client.engine.EngineClient
import org.nullversionnova.common.Axis
import org.nullversionnova.common.Direction
import org.nullversionnova.common.Direction.*
import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.Server
import org.nullversionnova.server.WorldCell

class Client : ApplicationListener, InputProcessor {
    // Members
    private val world = RenderedWorld()
    private val server = Server()
    private val registry = ClientRegistry()
    private val camera = OrthographicCamera()
    private lateinit var renderer : OrthogonalTiledMapRenderer
    private lateinit var batch : SpriteBatch
    private var buffer = 0
    private var w = 0
    private var h = 0
    private var zoom : Float = 1f

    private val current = Vector3()
    private val last = Vector3(-1f,-1f,-1f)
    private val delta = Vector3()

    private var loadedCellAddresses = mutableSetOf<IntVector3>()

    // Application
    override fun create() {
        Gdx.input.inputProcessor = this
        w = Gdx.graphics.width
        h = Gdx.graphics.height
        registry.initialize()
        SettlementClient.loadAssets(registry)
        EngineClient.loadAssets(registry)
        server.loadPacks()
        loadedCellAddresses = getLoadedCellsNearCamera()
        world.initialize(registry)
        batch = SpriteBatch()
        camera.setToOrtho(false, 30f, 30f)
        renderer = OrthogonalTiledMapRenderer(world.reloadMap(server.loadedCells,loadedCellAddresses), (1f / scale.toFloat()))
        camera.position.set(1000f,1000f,0f)
    }

    override fun resize(width: Int, height: Int) {
        w = width
        h = height
        camera.setToOrtho(false, 30f * (w.toFloat() / h.toFloat()), 30f)
    }

    override fun render() {
        // World Rendering
        ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f)
        camera.zoom = zoom
        camera.update()
        for (i in 0 until renderer.map.layers.count) {
            renderer.setView(camera)
            batch.begin()
            batch.draw(registry.getTexture(Identifier(EngineClient.pack_identifier,"fog")),0f,0f,w.toFloat(),h.toFloat())
            batch.end()
            renderer.render(intArrayOf(i))
            camera.zoom -= 0.01f
            camera.update()
        }

        // Game Processing
        changeCameraPosition()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
        registry.destroy()
    }

    // Input
    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.LEFT -> if (world.direction == NORTH || world.direction == EAST) {
                world.direction = world.direction.counterClockwise()
                buffer = camera.position.x.toInt()
                camera.position.x = WorldCell.CELL_SIZE - world.depth.toFloat()
                world.depth = buffer
                changeDepth()
                resetMap()
            }
            else if (world.direction == SOUTH || world.direction == WEST) {
                world.direction = world.direction.counterClockwise()
                buffer = WorldCell.CELL_SIZE - camera.position.x.toInt()
                camera.position.x = world.depth.toFloat()
                world.depth = buffer
                changeDepth()
                resetMap()
            }
            Input.Keys.UP -> if (!world.direction.isVertical()) {
                world.direction = UP
                changeDepth()
                resetMap()
            }
            else if (world.direction == DOWN) {
                world.direction = NORTH
                changeDepth()
                resetMap()
            }
            Input.Keys.RIGHT -> when (world.direction) {
                EAST -> {
                    world.direction = NORTH
                    buffer = camera.position.x.toInt()
                    camera.position.x = WorldCell.CELL_SIZE - world.depth.toFloat()
                    world.depth = buffer
                    changeDepth()
                    resetMap()
                }
                WEST -> {
                    world.direction = SOUTH
                    buffer = WorldCell.CELL_SIZE - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    changeDepth()
                    resetMap()
                }
                NORTH -> {
                    world.direction = WEST
                    buffer = camera.position.x.toInt()
                    camera.position.x = WorldCell.CELL_SIZE - world.depth.toFloat()
                    world.depth = buffer
                    changeDepth()
                    resetMap()
                }
                SOUTH -> {
                    world.direction = EAST
                    buffer = WorldCell.CELL_SIZE - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    changeDepth()
                    resetMap()
                }
                else -> return false
            }
            Input.Keys.DOWN -> when (world.direction) {
                EAST, WEST, NORTH, SOUTH -> {
                    world.direction = DOWN
                    changeDepth()
                    resetMap()
                }
                UP -> {
                    world.direction = NORTH
                    changeDepth()
                    resetMap()
                }
                DOWN -> return false
            }
            Input.Keys.PAGE_DOWN -> {
                world.depth = RenderedWorld.depthDirection(world.depth,world.direction,-1)
                changeDepth()
                resetMapViaDepth(false)
            }
            Input.Keys.PAGE_UP -> {
                world.depth = RenderedWorld.depthDirection(world.depth,world.direction,1)
                changeDepth()
                resetMapViaDepth(true)
            }
            else -> return false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        when (character) {
            'w' -> camera.translate(0f,0.5f)
            'a' -> camera.translate(-0.5f,0f)
            's' -> camera.translate(0f,-0.5f)
            'd' -> camera.translate(0.5f,0f)
            'm' -> {
                val setToUnload = mutableSetOf<IntVector3>()
                if (loadedCellAddresses.isEmpty()) {
                    loadedCellAddresses = getLoadedCellsNearCamera()
                } else {
                    for (i in server.loadedCells.keys) {
                        server.unloadCell(i)
                        setToUnload.add(i)
                    }
                    for (i in setToUnload) {
                        server.loadedCells.remove(i)
                    }
                    loadedCellAddresses.clear()
                }
                renderer.map.dispose()
                renderer.map = world.reloadMap(server.loadedCells,loadedCellAddresses)
            }
            else -> return false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        when (button) {
//            Buttons.RIGHT -> {
//                camera.unproject(current.set(screenX.toFloat(), screenY.toFloat(), 0f))
//                val x = current.x.toInt()
//                val y = current.y.toInt()
//                when (world.direction) {
//                    0 -> {
//                        println(server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(world.depth,x,y), Identifier("sand")))
//                    }
//                    1 -> {
//                        server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(world.depth,64 - x,64 - y),Identifier("sand"))
//                    }
//                    2 -> {
//                        server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(x,world.depth,y), Identifier("sand"))
//                    }
//                    3 -> {
//                        server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(64 - x,world.depth,64 - y),Identifier("sand"))
//                    }
//                    4 -> {
//                        server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(x,y,world.depth), Identifier("sand"))
//                    }
//                    5 -> {
//                        server.loadedCells[IntegerVector3()]?.tilemap?.changeTile(IntegerVector3(64 - x,64 - y,world.depth), Identifier("sand"))
//                    }
//                }
//                renderer.map = world.reloadMap(server.loadedCells)
//                return true
//            }
//
//        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        last[-1f, -1f] = -1f
        return true
    }

    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        camera.unproject(current.set(x.toFloat(), y.toFloat(), 0f))
        if (!(last.x == -1f && last.y == -1f && last.z == -1f)) {
            camera.unproject(delta.set(last.x, last.y, 0f))
            delta.sub(current)
            camera.position.add(delta.x, delta.y, 0f)
        }
        last.set(x.toFloat(), y.toFloat(), 0f)
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        zoom += amountY / 5
        if (camera.zoom.isNaN()) {
            camera.zoom = zoom
        }
        if (zoom > 1.5) {
            zoom = 1.5f
        }
        return true
    }

    // Auxiliary
    fun getLoadedCellsNearCamera(cameraCoordinates: IntVector3 = world.cameraCellCoordinates) : MutableSet<IntVector3> {
        val set = mutableSetOf<IntVector3>()
        val setToUnload = mutableSetOf<IntVector3>()
        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    val newVector = cameraCoordinates.copy()
                    newVector.x += i
                    newVector.y += j
                    newVector.z += k
                    set.add(newVector)
                }
            }
        }
        for (i in set) {
            if (server.loadedCells[i] == null) {
                server.loadCell(i)
            }
            if (server.loadedCells[i]?.loaded == false) {
                server.loadedCells[i]?.generate()
            }
        }
        for (i in server.loadedCells.keys) {
            if (!set.contains(i)) {
                server.unloadCell(i)
                setToUnload.add(i)
            }
        }
        for (i in setToUnload) {
            server.loadedCells.remove(i)
        }
        return set
    }
    fun changeDepth() {
        if (world.depth >= WorldCell.CELL_SIZE && world.direction.axis() == Axis.X) {
            world.cameraCellCoordinates.x += 1
            world.depth -= WorldCell.CELL_SIZE
        } else if (world.depth < 0 && world.direction.axis() == Axis.X) {
            world.cameraCellCoordinates.x -= 1
            world.depth += WorldCell.CELL_SIZE
        } else if (world.depth >= WorldCell.CELL_SIZE && world.direction.axis() == Axis.Y) {
            world.cameraCellCoordinates.y += 1
            world.depth -= WorldCell.CELL_SIZE
        } else if (world.depth < 0 && world.direction.axis() == Axis.Y) {
            world.cameraCellCoordinates.y -= 1
            world.depth += WorldCell.CELL_SIZE
        } else if (world.depth >= WorldCell.CELL_SIZE && world.direction.axis() == Axis.Z) {
            world.cameraCellCoordinates.z += 1
            world.depth -= WorldCell.CELL_SIZE
        } else if (world.depth < 0 && world.direction.axis() == Axis.Z) {
            world.cameraCellCoordinates.z -= 1
            world.depth += WorldCell.CELL_SIZE
        }
    }
    fun resetMapViaDepth(polarity: Boolean) {
        loadedCellAddresses = getLoadedCellsNearCamera()
        if (polarity) {
            renderer.map = world.advanceDepth(server.loadedCells,loadedCellAddresses,renderer.map)
        } else {
            renderer.map = world.recedeDepth(server.loadedCells,loadedCellAddresses,renderer.map)
        }
    }
    fun resetMap() {
        loadedCellAddresses = getLoadedCellsNearCamera()
        renderer.map.dispose()
        renderer.map = world.reloadMap(server.loadedCells,loadedCellAddresses)
    }
    fun changeCameraPosition() {
        val increasex = when (world.direction) {
            SOUTH, WEST -> -1
            else -> 1
        }
        val increasey = if (world.direction == UP) { -1 } else { 1 }
        var isChange = false
        if (camera.position.x < 32) {
            camera.position.x += 32
            world.cameraCellCoordinates.setAxis(world.cameraCellCoordinates.getAxis(world.direction.axis().getOtherPair().first) - increasex,world.direction.axis().getOtherPair().first)
            isChange = true
        } else if (camera.position.x > 64) {
            camera.position.x -= 32
            world.cameraCellCoordinates.setAxis(world.cameraCellCoordinates.getAxis(world.direction.axis().getOtherPair().first) + increasex,world.direction.axis().getOtherPair().first)
            isChange = true
        }
        if (camera.position.y < 32) {
            camera.position.y += 32
            world.cameraCellCoordinates.setAxis(world.cameraCellCoordinates.getAxis(world.direction.axis().getOtherPair().second) - increasey,world.direction.axis().getOtherPair().second)
            isChange = true
        } else if (camera.position.y > 64) {
            camera.position.y -= 32
            world.cameraCellCoordinates.setAxis(world.cameraCellCoordinates.getAxis(world.direction.axis().getOtherPair().second) + increasey,world.direction.axis().getOtherPair().second)
            isChange = true
        }
        if (isChange) {
            changeDepth()
            resetMap()
        }
    }

    // Companions
    companion object Global {
        const val scale = 8
        fun getTileTexture(direction: Direction, identifier: Identifier): Identifier {
            val data = Klaxon().parse<TileTextureData>(Gdx.files.internal("${identifier.pack}/models/tiles/${identifier.name}.json").readString())
                ?: return Identifier("core","default")
            if (direction == UP) {
                return data.bottom
            }
            if (direction == DOWN) {
                return data.top
            }
            return data.side
        }
    }


}
