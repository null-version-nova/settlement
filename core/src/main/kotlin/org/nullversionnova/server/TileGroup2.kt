package org.nullversionnova.server

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector2

data class TileGroup2(val cornerA: IntegerVector2, val cornerB: IntegerVector2, val identifier: Identifier) {
    fun getGreaterOnAxis(axis: Int) : IntegerVector2 {
        return if (cornerA.getAxisFromInt(axis) > cornerB.getAxisFromInt(axis)) {
            cornerA
        } else {
            cornerB
        }
    }
    fun getLesserOnAxis(axis: Int) : IntegerVector2 {
        return if (cornerA.getAxisFromInt(axis) < cornerB.getAxisFromInt(axis)) {
            cornerA
        } else {
            cornerB
        }
    }
}
