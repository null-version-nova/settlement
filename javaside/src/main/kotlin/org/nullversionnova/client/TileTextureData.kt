package org.nullversionnova.client

import org.nullversionnova.data.Identifier

data class TileTextureData(val top: Identifier, val side: Identifier = top, val bottom: Identifier = side)
