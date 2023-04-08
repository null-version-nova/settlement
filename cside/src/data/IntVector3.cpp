//
// Created by TheAviary on 4/7/2023.
//

#include "IntVector3.h"

IntVector3::IntVector3(int x, int y, int z) {
    this->x = x;
    this->y = y;
    this->z = z;
}

IntVector3::IntVector3(Vector3 vector3) {
    x = vector3.x;
    y = vector3.y;
    z = vector3.z;
}

const Vector3 IntVector3::toVector3() {
    Vector3 vector3;
    vector3.x = x;
    vector3.y = y;
    vector3.z = z;
    return vector3;
}

const int IntVector3::getAxis(Axis axis) {
    switch (axis) {
        case X: return x;
        case Y: return y;
        case Z: return z;
    }
}

IntVector3::IntVector3(IntVector2 vector2) {
    this->x = vector2.x;
    this->y = vector2.y;
}

void IntVector3::setAxis(Axis axis, int input) {
    if (axis == X) {
        x = input;
    } else if (axis == Y) {
        y = input;
    } else {
        z = input;
    }
}
