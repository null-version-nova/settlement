//
// Created by TheAviary on 3/19/2023.
//

#ifndef SETTLEMENT_BUILDER_NVN_ENTITY_H
#define SETTLEMENT_BUILDER_NVN_ENTITY_H
#include <raylib.h>
#include <string>
using namespace std;

class Entity {
protected:
    int health;
public:
    // Constructors
    Entity() = default;
    ~Entity() = default;

    // Members
    Image sprite;
    string entitytypename;
    string personalname;

    // Virtual Methods
    virtual void tick() = 0;

    // Methods
    /**
     * @param alterAmount
     * @return new health value
     */
    int alterHealth(int alterAmount);

    /**
     * @return health value
     */
    int getHealth() const;
};


#endif //SETTLEMENT_BUILDER_NVN_ENTITY_H
