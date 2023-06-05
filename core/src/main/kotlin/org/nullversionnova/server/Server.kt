package org.nullversionnova.server

import org.nullversionnova.common.Identifier
import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.entities.Entity
import org.nullversionnova.server.entities.MobileEntity
import org.nullversionnova.server.world.WorldCell
import org.nullversionnova.server.settlement.Settlement
import org.nullversionnova.server.tiles.TileInstance
import org.nullversionnova.server.tiles.TileState

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
    }
    fun tick() {
        loadedCell.tick(this,tickIndex)
        for(i in entities) {
            i.tick(this)
        }
    }
    operator fun get(location: IntVector3) : TileState {
        if (loadedCell[location] == null) {
            TileState.getTileState(TileInstance.instanceTile(Identifier("engine","air"),location,this),this)
        }
        return TileState.getTileState(loadedCell[location]!!,this)
    }
}
