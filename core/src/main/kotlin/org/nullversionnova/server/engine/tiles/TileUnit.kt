package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.IntVector3
import org.nullversionnova.server.engine.GameObject

data class TileUnit(override val location : IntVector3, override val tile: GameObject) : TileStorage
