//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_MOBILEENTITY_H
#define SETTLEMENT_BUILDER_NVN_MOBILEENTITY_H

#include "src/entities/Entity.h"

class MobileEntity : public Entity {
private:
    double GRAVITY = 1;
    double TERMINAL_VELOCITY = -1;

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