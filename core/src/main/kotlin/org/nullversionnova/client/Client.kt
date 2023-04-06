package org.nullversionnova.client

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.beust.klaxon.Klaxon
import org.nullversionnova.client.base.BaseClient
import org.nullversionnova.client.core.CoreClient
import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3
import org.nullversionnova.server.Server

class Client : ApplicationListener, InputProcessor {
    // Members
    private val world = RenderedWorld()
    private val server = Server()
    private val registry = ClientRegistry()
    private val camera = OrthographicCamera()
    private lateinit var renderer : OrthogonalTiledMapRenderer
    var buffer = 0
    var w = 0
    var h = 0

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
        CoreClient.loadAssets(registry)
        server.loadPacks()
        server.loadCell(IntegerVector3(0,0,0))
        world.initialize(registry)
        camera.setToOrtho(false, 30f, 30f)
        server.loadedCells[IntegerVector3(0,0,0)]?.generate()
        renderer = OrthogonalTiledMapRenderer(world.reloadMap(server.loadedCells), (1f / scale.toFloat()))
    }

    override fun resize(width: Int, height: Int) {
        w = width
        h = height
        camera.setToOrtho(false, 30f * (w.toFloat() / h.toFloat()), 30f)
    }

    override fun render() {
        ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f)
        camera.update()

        renderer.setView(camera)
        renderer.render()
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
            Input.Keys.LEFT -> when (world.direction) {
                0 -> {
                    world.direction = 3
                    buffer = camera.position.x.toInt()
                    camera.position.x = 64 - world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                1 -> {
                    world.direction = 2
                    buffer = 64 - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                     }
                2 -> {
                    world.direction = 0
                    buffer = camera.position.x.toInt()
                    camera.position.x = 64 - world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                3 -> {
                    world.direction = 1
                    buffer = 64 - camera.position.x.toInt()
                    camera.position.x = world.depth.toFloat()
                    world.depth = buffer
                    renderer.map = world.reloadMap(server.loadedCells)
                }
            }
            Input.Keys.UP -> when (world.direction) {
                0, 1, 2, 3 -> {
                    world.direction = 4
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                5 -> {
                    world.direction = 0
                    renderer.map = world.reloadMap(server.loadedCells)
                }
            }
            Input.Keys.RIGHT -> when (world.direction) {
                0 -> {
                    world.direction = 2
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                1 -> {
                    world.direction = 3
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                2 -> {
                    world.direction = 1
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                3 -> {
                    world.direction = 0
                    renderer.map = world.reloadMap(server.loadedCells)
                }
            }
            Input.Keys.DOWN -> when (world.direction) {
                0, 1, 2, 3 -> {
                    world.direction = 5
                    renderer.map = world.reloadMap(server.loadedCells)
                }
                4 -> {
                    world.direction = 0
                    renderer.map = world.reloadMap(server.loadedCells)
                }
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
        println(world.direction)
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
            else -> return false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        last[-1f, -1f] = -1f
        println(camera.position.x)
        println(camera.position.y)
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
        camera.zoom += amountY / 4
        return true
    }

    // Companions
    companion object Global {
        const val scale = 8
        fun getTileTexture(direction: Int, identifier: Identifier): Identifier {
            val data = Klaxon().parse<TileTextureData>(Gdx.files.internal("${identifier.pack}/models/tiles/${identifier.name}.json").readString())
                ?: return Identifier("core","default")
            if (direction == 4) {
                return data.bottom
            }
            if (direction == 5) {
                return data.top
            }
            return data.side
        }
    }


}
