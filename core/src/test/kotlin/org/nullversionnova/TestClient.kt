package org.nullversionnova

import ktx.app.KtxApplicationAdapter

class TestClient(val testFunction: () -> Unit) : KtxApplicationAdapter {
    override fun create() {
        testFunction()
    }
}
