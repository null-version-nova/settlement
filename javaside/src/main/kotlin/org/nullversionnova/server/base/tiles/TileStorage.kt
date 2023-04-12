package org.nullversionnova.server.base.tiles

import org.nullversionnova.data.IntegerVector3

interface TileStorage {
    val location : IntegerVector3
    val tile : TileInstance
}
