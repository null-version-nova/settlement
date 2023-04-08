//
// Created by TheAviary on 3/19/2023.
//

#include "StaticEntity.h"

ItemEntity StaticEntity::remove() {
    ItemEntity item;
    item.personalname = personalname;
    item.entitytypename = "static_entity_item";
    item.data["static_entity_type"] = entitytypename;
    for (pair<string, string> i: materials) {
        item.data[i.first] = i.second;
    }
    return item;
}