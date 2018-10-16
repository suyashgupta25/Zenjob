package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class OfferDeclineReasonsResponse(
        val reasons: List<ReasonsItem>? = null
)
