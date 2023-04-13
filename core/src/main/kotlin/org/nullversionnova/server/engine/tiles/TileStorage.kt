package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3

interface TileStorage {
    val location : IntVector3
    val tile : TileInstance
}
