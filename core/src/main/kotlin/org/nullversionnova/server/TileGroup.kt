package org.nullversionnova.server

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3

data class TileGroup(val cornerA: IntegerVector3, val cornerB: IntegerVector3, val identifier: Identifier)
