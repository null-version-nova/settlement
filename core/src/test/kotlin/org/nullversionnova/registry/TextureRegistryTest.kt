package org.nullversionnova.registry

import org.nullversionnova.launchGdxRuntime
import kotlin.test.Test

class TextureRegistryTest {
    @Test
    fun testLoadAndDispose() {
        launchGdxRuntime {
            val registry = TextureRegistry(MutableRegistry())
            registry.register()
            registry.dispose()
        }
    }
}
