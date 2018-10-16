package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BreakTypesItem(
        val minutes: Int? = null,
        val description: String? = null
)
