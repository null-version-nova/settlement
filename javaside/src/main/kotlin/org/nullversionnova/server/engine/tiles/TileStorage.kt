package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntegerVector3

interface TileStorage {
    val location : IntegerVector3
    val tile : TileInstance
}
