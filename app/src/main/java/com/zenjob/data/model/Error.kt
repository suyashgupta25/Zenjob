package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Error(
        val code: String? = null,
        val message: String? = null
)
