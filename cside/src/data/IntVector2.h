//
// Created by TheAviary on 4/7/2023.
//

#ifndef SETTLEMENT_INTVECTOR2_H
#define SETTLEMENT_INTVECTOR2_H


#include <raylib.h>
#include "Enumerations.h"
#include "IntVector3.h"

class IntVector2 {
public:
    // Properties
    int x = 0;
    int y = 0;

    // Constructors
    IntVector2(int x, int y);
    IntVector2(Vector2 vector2);
    IntVector2(IntVector3 vector3);
    IntVector2() = default;

    // Methods
    const Vector2 toVector2();
    const int getAxis(Axis axis);
    void setAxis(Axis axis, int input);
};


#endif //SETTLEMENT_INTVECTOR2_H
