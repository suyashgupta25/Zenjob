package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiConstructor

@JsonSerializable
data class OfferDeclineRequest @KotshiConstructor constructor(
        var reason: String? = null,
        var reasonComment: String? = null
) {
}