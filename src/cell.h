#ifndef SETTLEMENT_BUILDER_NVN_CELL_H
#define SETTLEMENT_BUILDER_NVN_CELL_H

#include <string>
#include <raylib.h>
#include <vector>
#include "entities/Entity.h"
#include "entities/static/StaticEntity.h"

using namespace std;

class Cell {
private:
    const static int size = 128;
public:
    // Members
    string cellContent[size][size][size];
    Vector3 location;
    vector<StaticEntity*> staticEntityList;
};


#endif //SETTLEMENT_BUILDER_NVN_CELL_H

// ~nvn