//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_MOBILEENTITY_H
#define SETTLEMENT_BUILDER_NVN_MOBILEENTITY_H

#include <raylib.h>
#include "entities/Entity.h"

class MobileEntity : public Entity {
private:
    constexpr static const double GRAVITY = 1;
    constexpr static const double TERMINAL_VELOCITY = -1;

public:
    // Members
    Vector3 position;
    Vector3 velocity;
    bool adjacentSurfaces[6];
    bool isGravityAffected = true;

    // Methods
    void tick() override;
};

#endif //SETTLEMENT_BUILDER_NVN_MOBILEENTITY_H