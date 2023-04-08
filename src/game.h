//
// Created by TheAviary on 3/17/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_GAME_H
#define SETTLEMENT_BUILDER_NVN_GAME_H
#include <vector>
#include "entities/mobile/MobileEntity.h"
#include "cell.h"

using namespace std;

class game {
public:
    // Members
    vector<MobileEntity*> mobileEntityList;
    vector<Cell> cellList;
    int cameraOrientation;
    int cameraPosition;
    Camera2D camera;

    // Methods
    void mainLoop();
    void renderLoop();
};


#endif //SETTLEMENT_BUILDER_NVN_GAME_H
