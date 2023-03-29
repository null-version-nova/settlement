package org.nullversionnova

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.WorldCell
import kotlin.math.floor

object GlobalObjects {
    // Functions
    fun convertPositionToLocal(globalPosition: Vector3) : Vector3 {
        return Vector3(globalPosition.x % WorldCell.CELL_SIZE_X,globalPosition.y % WorldCell.CELL_SIZE_Y, globalPosition.z % WorldCell.CELL_SIZE_Z)
    }
    fun convertPositionToCell(globalPosition: Vector3) : IntegerVector3 {
        return IntegerVector3(
            floor(globalPosition.x / WorldCell.CELL_SIZE_X),
            floor(globalPosition.y / WorldCell.CELL_SIZE_Y),
            floor(globalPosition.z / WorldCell.CELL_SIZE_Z)
        )
    }
    fun convertPositionToGlobal(localPosition : Vector3, cellLocation : IntegerVector3) : Vector3 {
        return Vector3(cellLocation.x * 128 + localPosition.x,cellLocation.y * 128 + localPosition.y,cellLocation.z * 128 + localPosition.z)
    }
    fun roundPosition(position: Vector3) : Vector3 {
        return Vector3(floor(position.x),floor(position.y),floor(position.z))
    }
}
