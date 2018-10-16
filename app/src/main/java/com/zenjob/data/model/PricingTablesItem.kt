package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PricingTablesItem(
        val times: Any? = null,
        val earningTotal: String? = null,
        val minutes: Int? = null,
        val earningHourly: String? = null,
        val name: String? = null,
        val isSummary: Boolean? = null,
        val unpaid: Boolean? = null
)
