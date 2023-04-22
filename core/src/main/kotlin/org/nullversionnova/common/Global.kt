package org.nullversionnova.common

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.engine.cell.WorldCell
import kotlin.math.floor

object Global {
    // Functions
    fun convertPositionToLocal(globalPosition: Vector3) : Vector3 {
        return Vector3(globalPosition.x % WorldCell.CELL_SIZE,globalPosition.y % WorldCell.CELL_SIZE, globalPosition.z % WorldCell.CELL_SIZE)
    }
    fun convertPositionToCell(globalPosition: Vector3) : IntVector3 {
        return IntVector3(
            floor(globalPosition.x / WorldCell.CELL_SIZE),
            floor(globalPosition.y / WorldCell.CELL_SIZE),
            floor(globalPosition.z / WorldCell.CELL_SIZE)
        )
    }
    fun convertPositionToGlobal(localPosition : Vector3, cellLocation : IntVector3) : Vector3 {
        return Vector3(cellLocation.x * 128 + localPosition.x,cellLocation.y * 128 + localPosition.y,cellLocation.z * 128 + localPosition.z)
    }
    fun roundPosition(position: Vector3) : Vector3 {
        return Vector3(floor(position.x),floor(position.y),floor(position.z))
    }
}
