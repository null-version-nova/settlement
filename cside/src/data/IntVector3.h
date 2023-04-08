//
// Created by TheAviary on 4/7/2023.
//

#ifndef SETTLEMENT_INTVECTOR3_H
#define SETTLEMENT_INTVECTOR3_H

#include <raylib.h>
#include "IntVector2.h"

class IntVector3 {
public:
    // Properties
    int x = 0;
    int y = 0;
    int z = 0;

    // Constructors
    IntVector3(int x, int y, int z);
    IntVector3(Vector3 vector3);
    IntVector3(IntVector2 vector2);
    IntVector3() = default;

    // Methods
    const Vector3 toVector3();
    const int getAxis(Axis axis);
    void setAxis(Axis axis, int input);
};


#endif //SETTLEMENT_INTVECTOR3_H
