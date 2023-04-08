//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_STATICENTITY_H
#define SETTLEMENT_BUILDER_NVN_STATICENTITY_H

#include "src/entities/Entity.h"
#include "src/entities/mobile/ItemEntity.h"
#include <map>
using namespace std;

class StaticEntity : public Entity {
public:
    // Members
    int location[128][128][128];
    map<string,string> materials;

    // Methods
    ItemEntity remove();
};

#endif //SETTLEMENT_BUILDER_NVN_STATICENTITY_H
