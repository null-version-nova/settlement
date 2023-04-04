package org.nullversionnova.data

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
    override fun equals(other: Any?) : Boolean {
        val group1 : Array<Int> = arrayOf(4)
        val group2 : Array<Int> = arrayOf(4)
        if (other !is TileGroup2) { return super.equals(other) }
        if (cornerA.x < cornerB.x) {
            group1[0] = cornerA.x
            group1[1] = cornerB.x
        } else {
            group1[0] = cornerB.x
            group1[1] = cornerA.x
        }
        if (other.cornerA.x < other.cornerB.x) {
            group2[0] = cornerA.x
            group2[1] = cornerB.x
        } else {
            group2[0] = cornerB.x
            group2[1] = cornerA.x
        }
        if (cornerA.y < cornerB.y) {
            group1[2] = cornerA.y
            group1[3] = cornerB.y
        } else {
            group1[2] = cornerB.y
            group1[3] = cornerA.y
        }
        if (other.cornerA.y < other.cornerB.y) {
            group2[2] = cornerA.y
            group2[3] = cornerB.y
        } else {
            group2[2] = cornerB.y
            group2[3] = cornerA.y
        }
        return (group1.contentEquals(group2) && identifier == other.identifier)
    }
    override fun hashCode(): Int {
        var result = cornerA.hashCode()
        result = 31 * result + cornerB.hashCode()
        result = 31 * result + identifier.hashCode()
        return result
    }
}
