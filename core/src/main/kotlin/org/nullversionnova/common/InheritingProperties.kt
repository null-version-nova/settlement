package org.nullversionnova.common

open class InheritingProperties(private val parent : InheritingProperties? = null) : Properties() {
    // Members
    private val propertyNegation = mutableSetOf<Identifier>()

    // Methods
    override fun get(identifier: Identifier): Number? {
        return if (super.get(identifier) == null) {
            if (parent == null) {
                null
            } else {
                parent[identifier]
            }
        } else {
            super.get(identifier)
        }
    }
    override fun hasProperty(identifier: Identifier): Boolean {
        return if (super.hasProperty(identifier)) {
            true
        } else if (propertyNegation.contains(identifier) || parent == null) {
            false
        } else {
            parent.hasProperty(identifier)
        }
    }
    override fun makeNot(identifier: Identifier): Boolean {
        return if (hasProperty(identifier)) {
            propertyNegation.add(identifier)
            true
        } else {
            false
        }
    }
}
