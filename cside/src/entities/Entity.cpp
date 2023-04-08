//
// Created by TheAviary on 4/7/2023.
//
#include "Entity.h"

int Entity::alterHealth(int alterAmount) {
    health += alterAmount;
    return health;
}

int Entity::getHealth() const {
    return health;
}

