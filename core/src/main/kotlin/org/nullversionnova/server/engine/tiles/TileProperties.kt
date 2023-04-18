package org.nullversionnova.server.engine.tiles

import org.nullversionnova.common.Identifier

class TileProperties(var material: Identifier = Identifier()) {
    var isFluid = false
    var isGas = false
    var hardness : Int = 0
    fun setHardness(newHardness: Int) : TileProperties {
        hardness = if (newHardness < 0) { 0 }
        else { newHardness }
        return this
    }
    fun isFluid() : TileProperties {
        isFluid = true
        return this
    }
    fun isGas() : TileProperties {
        isGas = true
        return this
    }
    fun material(identifier: Identifier) : TileProperties {
        material = identifier
        return this
    }
}
