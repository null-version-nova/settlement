package org.nullversionnova.server.base.tiles

import org.nullversionnova.data.Identifier
import org.nullversionnova.data.IntegerVector3

interface TileInstance {
    var location: IntegerVector3
    var identifier: Identifier
}
