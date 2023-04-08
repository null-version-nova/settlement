//
// Created by TheAviary on 4/7/2023.
//

#ifndef SETTLEMENT_AXIS_H
#define SETTLEMENT_AXIS_H

#endif //SETTLEMENT_AXIS_H

// Enums
enum Axis { X, Y, Z };
enum Direction { NORTH, SOUTH, EAST, WEST, UP, DOWN };

namespace axis {
    // Enum Functions
    Axis getAxis(Direction direction) {
        switch (direction) {
            case NORTH:
                return Y;
            case SOUTH:
                return Y;
            case EAST:
                return X;
            case WEST:
                return X;
            case UP:
                return Z;
            case DOWN:
                return Z;
        }
    }
    bool isPositive(Direction direction) {
        if (direction == NORTH || direction == EAST || direction == UP) { return true; }
        else { return false; }
    }
    Direction clockwise(Direction direction) {
        switch (direction) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
        }
    }
    Direction cclockwise(Direction direction) {
        switch (direction) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
        }
    }
}

