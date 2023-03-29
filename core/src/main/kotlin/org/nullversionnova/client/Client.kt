package org.nullversionnova.client

import OrthoCamController
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.beust.klaxon.Klaxon
import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3
import org.nullversionnova.client.base.BaseClient
import org.nullversionnova.server.Server

class Client : ApplicationListener, InputProcessor {
    // Members
    private val world = RenderedWorld()
    private val server = Server()
    private val registry = ClientRegistry()
    private val camera = OrthographicCamera()
    private lateinit var renderer : OrthogonalTiledMapRenderer
    private var dragging = false
    private var touchPoint = Vector3(0f,0f,0f)
    var w = 0
    var h = 0

    // Application
    override fun create() {
        Gdx.input.inputProcessor = OrthoCamController(camera)
        w = Gdx.graphics.width
        h = Gdx.graphics.height
        registry.initialize()
        BaseClient.loadAssets(registry)
        server.loadPacks()
        server.loadCell(IntegerVector3(0,0,0))
        world.initialize(registry)
        camera.setToOrtho(false, 30f, 30f)
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
            37 -> camera.translate(-0.5f,0f)
            38 -> camera.translate(0f,0.5f)
            39 -> camera.translate(0.5f,0f)
            40 -> camera.translate(0f,-0.5f)
            else -> return false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button != Input.Buttons.LEFT || pointer > 0) return false
        camera.unproject(touchPoint.set(screenX.toFloat(), screenY.toFloat(), 0f))
        dragging = true
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (!dragging) return false
        camera.unproject(touchPoint.set(screenX.toFloat(), screenY.toFloat(), 0f))
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    // Companions
    companion object Global {
        const val scale = 8
        fun getTileTexture(direction: Int, identifier: Identifier): Identifier {
            val data = Klaxon().parse<TileTextureData>(Gdx.files.internal("${identifier.pack}/models/tiles/${identifier.name}.json").readString())
                ?: return Identifier(BaseClient.pack_identifier,"default")
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
