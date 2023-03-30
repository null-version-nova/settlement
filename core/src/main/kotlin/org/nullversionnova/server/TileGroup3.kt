package org.nullversionnova.server

import org.nullversionnova.Identifier
import org.nullversionnova.IntegerVector3

data class TileGroup3(val cornerA: IntegerVector3, val cornerB: IntegerVector3, val identifier: Identifier) {
    fun getGreaterOnAxis(axis: Int) : IntegerVector3 {
        return if (cornerA.getAxisFromInt(axis) > cornerB.getAxisFromInt(axis)) {
            cornerA
        } else {
            cornerB
        }
    }
    fun getLesserOnAxis(axis: Int) : IntegerVector3 {
        return if (cornerA.getAxisFromInt(axis) < cornerB.getAxisFromInt(axis)) {
            cornerA
        } else {
            cornerB
        }
    }
}
