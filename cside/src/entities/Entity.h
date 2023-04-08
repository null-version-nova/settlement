//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_ENTITY_H
#define SETTLEMENT_BUILDER_NVN_ENTITY_H

#include "data/Identifier.h"
#include <string>
using namespace std;

class Entity {
public:
    // Constructors
    Entity() = delete;
    ~Entity() = default;

    // Members
    Identifier identifier;
    int health;

    // Virtual Methods
    virtual void tick() = 0;
    virtual void die() = 0;
};


#endif //SETTLEMENT_BUILDER_NVN_ENTITY_H
