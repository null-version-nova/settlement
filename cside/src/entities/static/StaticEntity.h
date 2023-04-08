//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_STATICENTITY_H
#define SETTLEMENT_BUILDER_NVN_STATICENTITY_H

#include <map>
#include "entities/Entity.h"
#include "entities/mobile/ItemEntity.h"
#include "data/IntVector3.h"

using namespace std;

class StaticEntity : public Entity {
public:
    // Members
    IntVector3 location;
    map<Identifier,Identifier> materials;

    // Methods
    ItemEntity remove();
};

#endif //SETTLEMENT_BUILDER_NVN_STATICENTITY_H
