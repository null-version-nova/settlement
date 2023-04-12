package org.nullversionnova.server.base.tiles

import org.nullversionnova.data.IntegerVector3

data class TileUnit(override val location : IntegerVector3, override val tile: TileInstance) : TileStorage
