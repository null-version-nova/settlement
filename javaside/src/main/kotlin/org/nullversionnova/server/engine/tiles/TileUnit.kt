package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntegerVector3

data class TileUnit(override val location : IntegerVector3, override val tile: TileInstance) : TileStorage
