package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LoginResponse(
        val access_token: String? = null,
        val refresh_token: String? = null,
        val roles: List<String?>? = null,
        val token_type: String? = null,
        val expires_in: Int? = null,
        val username: String? = null
)
