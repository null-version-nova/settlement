//
// Created by TheAviary on 3/17/2023.
//

#include "game.h"

void game::mainLoop() {
    for (MobileEntity* i : mobileEntityList) {
        i->tick();
    }
    for (Cell i : cellList) {
        for (StaticEntity* j : i.staticEntityList) {
            j->tick();
        }
    }
}

void game::renderLoop() {

}
