package tiles

class Tile(var type: String) {
    var isFluid = false
    var isGas = false
    var hardness : Int = 0
    fun setHardness(newHardness: Int) : Tile {
        hardness = if (newHardness < 0) { 0 }
        else { newHardness }
        return this
    }
    fun isFluid() : Tile {
        isFluid = true
        return this
    }
    fun isGas() : Tile {
        isGas = true
        return this
    }
}