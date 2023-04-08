//
// Created by TheAviary on 4/7/2023.
//

#include "IntVector2.h"

IntVector2::IntVector2(int x, int y) {
    this->x = x;
    this->y = y;
}

IntVector2::IntVector2(Vector2 vector2) {
    this->x = vector2.x;
    this->y = vector2.y;
}

const Vector2 IntVector2::toVector2() {
    Vector2 vector2;
    vector2.x = x;
    vector2.y = y;
    return vector2;
}

const int IntVector2::getAxis(Axis axis) {
    if (axis == X) {
        return x;
    }
    return y;
}

void IntVector2::setAxis(Axis axis, int input) {
    if (axis == X) {
        x = input;
    }
    else {
        y = input;
    }
}

IntVector2::IntVector2(IntVector3 vector3) {
    x = vector3.x;
    y = vector3.y;
}
