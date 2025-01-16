package org.nullversionnova

import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.world.entities.Entity
import org.nullversionnova.world.entities.MobileEntity
import org.nullversionnova.world.WorldCell
import org.nullversionnova.settlement.Settlement
import org.nullversionnova.world.tiles.TileInstance
import org.nullversionnova.world.tiles.TileState

class Server {
    // Members
    val loadedCell = WorldCell()
    val registry = ServerRegistry()
    val entities = mutableListOf<Entity>()
    private var tickIndex = 0

    // Methods
    fun initialize() {
        Engine.load(registry)
        Settlement.load(registry)
        loadedCell.generate(this)
        entities.add(MobileEntity(Identifier("settlement","snowman"), IntVector3(0,30,128)))
        println("Server initialized!")
    }
    fun tick() {
        loadedCell.tick(this,tickIndex)
        for(i in entities) {
            i.tick(this)
        }
    }
    operator fun get(location: IntVector3) : TileState {
        if (loadedCell[location] == null) {
            return TileState.getTileState(TileInstance.instanceTile(Identifier("engine","air"),location,this),this)
        }
        return TileState.getTileState(loadedCell[location]!!,this)
    }
}
