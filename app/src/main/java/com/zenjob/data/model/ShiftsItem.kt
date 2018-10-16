package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ShiftsItem(
        val beginDate: String? = null,
        val breakTypes: Int? = null,
        val endDate: String? = null
)
