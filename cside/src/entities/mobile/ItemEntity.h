//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_ITEMENTITY_H
#define SETTLEMENT_BUILDER_NVN_ITEMENTITY_H

#include "MobileEntity.h"
#include <map>
using namespace std;

class ItemEntity : public MobileEntity {
public:
    map<string,string> data;
};


#endif //SETTLEMENT_BUILDER_NVN_ITEMENTITY_H
