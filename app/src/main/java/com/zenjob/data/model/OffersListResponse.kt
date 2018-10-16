package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class OffersListResponse(
        val offers: List<OffersItem> = listOf(),
        val total: Int? = null,
        val offset: Int? = null,
        val max: Int? = null,
        val newestTimestamp: Long? = null
)
