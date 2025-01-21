package org.nullversionnova.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
//import com.beust.klaxon.Klaxon
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ktx.app.KtxApplicationAdapter
import ktx.app.KtxInputAdapter
import ktx.async.KtxAsync
import ktx.graphics.use
import org.nullversionnova.Server
import org.nullversionnova.math.Direction3
import org.nullversionnova.math.Direction3.DOWN
import org.nullversionnova.math.Direction3.UP
import org.nullversionnova.registry.Identifier
import org.nullversionnova.world.WorldCell
import org.nullversionnova.world.entities.Entity

class Client : KtxApplicationAdapter, KtxInputAdapter {
    // Members
    private val world = RenderedWorld()
    private val server = Server()
    private val camera = OrthographicCamera()
    private lateinit var renderer : OrthogonalTiledMapRenderer
    private lateinit var batch : SpriteBatch
    private var w = 0
    private var h = 0
    private var zoom : Float = 1f
    private var reloadNecessary = true
    private var scanLine = 0

    private val current = Vector3()
    private val last = Vector3(-1f,-1f,-1f)
    private val delta = Vector3()

    // Application
    override fun create() {
        KtxAsync.initiate()
        Gdx.input.inputProcessor = this
        w = Gdx.graphics.width
        h = Gdx.graphics.height
        ClientRegistries.textureRegistry.register()
        ScreenUtils.clear(190f / 255f, 205f / 255f, 255f / 255f, 1f)
        server.initialize()
        world.initialize()
        batch = SpriteBatch()
        camera.setToOrtho(false, 30f, 30f)
        renderer = OrthogonalTiledMapRenderer(TiledMap(), (1f / SCALE.toFloat()))
        camera.position.set(0f,0f,0f)
    }

    override fun resize(width: Int, height: Int) {
        w = width
        h = height
        camera.setToOrtho(false, 30f * (w.toFloat() / h.toFloat()), 30f)
    }

    override fun render() {
        // Rendering
        if (server.loadedCell.loaded) {
            if (reloadNecessary) { resetMap() }
            if (renderer.map.layers.count < RenderedWorld.renderDistance) {
                renderer.map = world.renderCastMore(server,renderer.map)
            }
            else if (scanLine < renderer.map.layers.count) {
                renderer.map = world.renderCastOver(scanLine,server,renderer.map)
                scanLine++
            } else {
                world.resetCull()
            }
        }

        // Display
        ScreenUtils.clear(190f / 255f, 205f / 255f, 255f / 255f, 1f)
        camera.zoom = zoom + PARALLAX * renderer.map.layers.count
        camera.update()
        val entities = grabEntities(server)
        for (i in 0 until renderer.map.layers.count) {
            renderer.setView(camera)
            batch.use {
                batch.draw(ClientRegistries.textureRegistry["engine:fog"],0f,0f,w.toFloat(),h.toFloat())
                batch.draw(ClientRegistries.textureRegistry["engine:fog"],0f,0f,w.toFloat(),h.toFloat())
            }
            try {
                renderer.render(intArrayOf(i))
            } catch (e: Exception) {
                throw Exception("Renderer failed to render at layer $i",e)
            }
            batch.use(camera) {
                try {
                    for (j in entities[i - (255 - world.depth)]) {
                        batch.draw(ClientRegistries.textureRegistry[j.identifier],j.location.x.toFloat(),j.location.y.toFloat(),1f,1f)
                    }
                } catch (_: Exception) {}
            }
            camera.zoom -= PARALLAX
            camera.update()
        }

        // Game Processing
        KtxAsync.launch {
            server.tick()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
        renderer.map.dispose()
        ClientRegistries.textureRegistry.dispose()
    }

    // Input
    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.PAGE_DOWN -> {
                if (world.depth < WorldCell.CELL_SIZE) {
                    world.depth++
                    resetMapViaDepth(false)
                    println(world.depth)
                }
            }
            Input.Keys.PAGE_UP -> {
                if (world.depth >= 0) {
                    world.depth--
                    resetMapViaDepth(true)
                    println(world.depth)
                }
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
                println(renderer.map.layers.count)
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

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        camera.unproject(current.set(screenX.toFloat(), screenY.toFloat(), 0f))
        if (!(last.x == -1f && last.y == -1f && last.z == -1f)) {
            camera.unproject(delta.set(last.x, last.y, 0f))
            delta.sub(current)
            camera.position.add(delta.x, delta.y, 0f)
        }
        last.set(screenX.toFloat(), screenY.toFloat(), 0f)
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
        if (zoom > MAX_ZOOM) {
            zoom = MAX_ZOOM
        } else if (zoom < 0) {
            zoom = 0f
        }
        return true
    }

    // Auxiliary
    private fun resetMapViaDepth(polarity: Boolean) {
        if (polarity) {
            renderer.map = world.advanceDepth(renderer.map)
            world.resetCull()
            renderer.map = world.renderCastOver(0,server,renderer.map)
            scanLine = 1
        } else {
            renderer.map = world.recedeDepth(server,renderer.map)
            renderer.map = world.renderCastOver(1,server,renderer.map)
            world.resetCull()
        }

    }
    private fun resetMap() {
        renderer.map = world.renderCast(server,renderer.map)
        reloadNecessary = false
    }

    private fun grabEntities(server: Server) : Array<MutableSet<Entity>> {
        val array = Array<MutableSet<Entity>>(WorldCell.CELL_SIZE) { _ -> mutableSetOf() }
        for (i in server.entities) {
            array[i.location.z].add(i)
        }
        return array
    }

    // Companions
    companion object {
        const val SCALE = 8
        const val PARALLAX = 0.00f
        const val MAX_ZOOM = 5f
        fun getTileTexture(direction: Direction3, identifier: Identifier): Identifier {
            return try {
                val data = Json.decodeFromString<TileModel>(Gdx.files.internal("client/${identifier.pack}/models/tiles/${identifier.name}.json").readString())
                when (direction) {
                    UP -> Identifier(data.bottom)
                    DOWN -> Identifier(data.top)
                    else -> Identifier(data.side)
                }
            } catch (_: Exception) {
                Identifier("engine", "default")
            }
        }
    }
}
