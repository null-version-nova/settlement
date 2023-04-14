package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.GameObject

interface TileStorage {
    val location : IntVector3
    val tile : GameObject
}
