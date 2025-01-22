package org.nullversionnova.settlement.client

import org.nullversionnova.registry.Identifier
import org.nullversionnova.settlement.Settlement

object SettlementClient {
    const val pack_identifier = Settlement.PACK_IDENTIFIER

    fun getId(input: String) : Identifier { return Identifier(pack_identifier, input)
    }
}
