package org.nullversionnova.settlement.world.items

import com.badlogic.gdx.graphics.Texture

class Item(val type: String, var name: String = String()) {
    // Members
    lateinit var components : MutableList<BaseItem>
    lateinit var sprite : Texture
}
