//
// Created by TheAviary on 4/7/2023.
//

#ifndef SETTLEMENT_IDENTIFIER_H
#define SETTLEMENT_IDENTIFIER_H

#include <string>
using namespace std;

// A string pair for identification of objects
class Identifier {
public:
    // Properties
    const string DEFAULT_PACK = "system";
    string pack; // Name of the pack the identifier is from
    string name; // Name of the individual object the identifier refers to

    // Constructors
    Identifier(string pack, string name);
    Identifier(string name);
    Identifier() = delete;
    ~Identifier() = default;
};


#endif //SETTLEMENT_IDENTIFIER_H
