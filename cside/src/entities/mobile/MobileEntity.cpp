//
// Created by TheAviary on 3/19/2023.
//

#include "MobileEntity.h"

void MobileEntity::tick() {
    // Adjacency Checks
    if ((velocity.x > 0 && adjacentSurfaces[0]) || (velocity.x < 0 && adjacentSurfaces[1])) {velocity.x = 0;}
    if ((velocity.y > 0 && adjacentSurfaces[2]) || (velocity.y < 0 && adjacentSurfaces[3])) {velocity.y = 0;}
    if ((velocity.z > 0 && adjacentSurfaces[4]) || (velocity.z < 0 && adjacentSurfaces[5])) {velocity.z = 0;}

    // Movement
    position.x += velocity.x;
    position.y += velocity.y;
    position.z += velocity.z;

    // Gravity
    if (!adjacentSurfaces[5] && velocity.z >= TERMINAL_VELOCITY && isGravityAffected) {velocity.z -= GRAVITY;}
}