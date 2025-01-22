package org.nullversionnova.client

import kotlinx.serialization.Serializable

@Serializable
data class TileModel(val top: String, val side: String = top, val bottom: String = side)
