package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3

data class TileUnit(override val location : IntVector3, override val tile: TileInstance) : TileStorage
