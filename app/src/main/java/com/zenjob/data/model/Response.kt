package com.zenjob.data.model

data class Response(
        val offers: List<OffersItem?>? = null,
        val total: Int? = null,
        val offset: Int? = null,
        val max: Int? = null,
        val newestTimestamp: Long? = null
)
