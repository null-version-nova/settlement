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
import org.nullversionnova.client.base.BaseClient
import org.nullversionnova.client.core.SystemClient
import org.nullversionnova.data.Direction
import org.nullversionnova.data.Direction.*
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
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

    // Application
    override fun create() {
        Gdx.input.inputProcessor = this
        w = Gdx.graphics.width
        h = Gdx.graphics.height
        registry.initialize()
        BaseClient.loadAssets(registry)
        SystemClient.loadAssets(registry)
        server.loadPacks()
        server.loadCell(IntegerVector3())
        world.initialize(registry)
        batch = SpriteBatch()
        camera.setToOrtho(false, 30f, 30f)
        server.loadedCells[IntegerVector3()]?.generate()
        renderer = OrthogonalTiledMapRenderer(world.reloadMap(server.loadedCells), (1f / scale.toFloat()))
    }

    override fun resize(width: Int, height: Int) {
        w = width
        h = height
        camera.setToOrtho(false, 30f * (w.toFloat() / h.toFloat()), 30f)
    }

    override fun render() {
        ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f)
        camera.zoom = zoom
        camera.update()
        for (i in 0 until renderer.map.layers.count) {
            renderer.setView(camera)
            batch.begin()
            batch.draw(registry.getTexture(Identifier("system","fog")),0f,0f,w.toFloat(),h.toFloat())
            batch.end()
            renderer.render(intArrayOf(i))
            camera.zoom -= 0.01f
            camera.update()
        }
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
                camera.position.x = WorldCell.getSizeFromAxis(world.direction.counterClockwise().axis()) - world.depth.toFloat()
                world.depth = buffer
                renderer.map = world.reloadMap(server.loadedCells)
            }
            else if (world.direction == SOUTH || world.direction == WEST) {
                world.direction = world.direction.counterClockwise()
                buffer = WorldCell.getSizeFromAxis(world.direction.counterClockwise().axis()) - camera.position.x.toInt()
                camera.position.x = world.depth.toFloat()
                world.depth = buffer
                renderer.map = world.reloadMap(server.loadedCells)
            }
            Input.Keys.UP -> if (!world.direction.isVertical()) {
                world.direction = UP
                renderer.map = world.reloadMap(server.loadedCells)
            }
            else if (world.direction == DOWN) {
                world.direction = NORTH
                renderer.map = world.reloadMap(server.loadedCells)
            }
            Input.Keys.RIGHT -> when (world.direction) {
                EAST -> {
                    world.direction = NORTH
                    buffer = camera.position.x.toInt()
                    camera.position.x = WorldCell.CELL_SIZE_Y - world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                WEST -> {
                    world.direction = SOUTH
                    buffer = WorldCell.CELL_SIZE_Y - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                NORTH -> {
                    world.direction = WEST
                    buffer = camera.position.x.toInt()
                    camera.position.x = WorldCell.CELL_SIZE_X - world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                SOUTH -> {
                    world.direction = EAST
                    buffer = WorldCell.CELL_SIZE_X - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                else -> return false
            }
            Input.Keys.DOWN -> when (world.direction) {
                EAST, WEST, NORTH, SOUTH -> {
                    world.direction = DOWN
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                UP -> {
                    world.direction = NORTH
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                DOWN -> return false
            }
            Input.Keys.PAGE_DOWN -> {
                world.depth--
                renderer.map = world.reloadMap(server.loadedCells)
            }
            Input.Keys.PAGE_UP -> {
                world.depth++
                renderer.map = world.reloadMap(server.loadedCells)
            }
            else -> return false
        }
        println(world.depth)
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
            'm' -> server.loadedCells[IntegerVector3()]?.generate()
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
        return false
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
        return true
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
