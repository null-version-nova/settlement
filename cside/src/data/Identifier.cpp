//
// Created by TheAviary on 4/7/2023.
//
#include "Identifier.h"

Identifier::Identifier(string pack, string name) {
    this->pack = pack;
    this->name = name;
}

Identifier::Identifier(string name) {
    this->pack = DEFAULT_PACK;
    this->name = name;
}
