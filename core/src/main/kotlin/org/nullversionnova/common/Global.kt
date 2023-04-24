package org.nullversionnova.common

import com.badlogic.gdx.math.Vector3
import org.nullversionnova.server.engine.cell.WorldCell
import kotlin.math.floor

object Global {
    // Functions
    fun convertPositionToLocal(globalPosition: IntVector3) : IntVector3 {
        return IntVector3(
            globalPosition.x - convertPositionToGlobal(convertPositionToCell(globalPosition)).x,
            globalPosition.y - convertPositionToGlobal(convertPositionToCell(globalPosition)).y,
            globalPosition.z - convertPositionToGlobal(convertPositionToCell(globalPosition)).z)
    }
    fun convertPositionToCell(globalPosition: IntVector3) : IntVector3 {
        return IntVector3(
            globalPosition.x.floorDiv(WorldCell.CELL_SIZE),
            globalPosition.y.floorDiv(WorldCell.CELL_SIZE),
            globalPosition.z.floorDiv(WorldCell.CELL_SIZE)
        )
    }
    fun convertPositionToGlobal(cellLocation : IntVector3, localPosition : IntVector3 = IntVector3()) : IntVector3 {
        return IntVector3(cellLocation.x * 128 + localPosition.x,cellLocation.y * 128 + localPosition.y,cellLocation.z * 128 + localPosition.z)
    }
    fun roundPosition(position: Vector3) : Vector3 {
        return Vector3(floor(position.x),floor(position.y),floor(position.z))
    }
}
