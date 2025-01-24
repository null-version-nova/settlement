package org.nullversionnova

import org.nullversionnova.registry.Identifier
import org.nullversionnova.math.IntVector3
import org.nullversionnova.world.entities.Entity
import org.nullversionnova.world.entities.MobileEntity
import org.nullversionnova.world.WorldCell
import org.nullversionnova.settlement.Settlement
import org.nullversionnova.settlement.world.tiles.SettlementTiles
import org.nullversionnova.world.tiles.EngineTiles
import org.nullversionnova.world.tiles.TileInstance

class Server {
    // Members
    val loadedCell = WorldCell()
    val entities = mutableListOf<Entity>()
    private var tickIndex = 0

    // Methods
    fun initialize() {
        EngineTiles
        SettlementTiles
        Engine.load()
        Settlement.load()
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
    operator fun get(location: IntVector3) : TileInstance {
        if (loadedCell[location] == null) {
            return TileInstance.instanceTile(EngineTiles.AIR,location,this)
        }
        return loadedCell[location]!!
    }
}
