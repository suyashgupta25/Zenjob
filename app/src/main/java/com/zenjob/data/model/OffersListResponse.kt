package com.zenjob.data.model

data class OffersListResponse(
        val offers: List<OffersItem> = listOf(),
        val total: Int? = null,
        val offset: Int? = null,
        val max: Int? = null,
        val newestTimestamp: Long? = null
)
