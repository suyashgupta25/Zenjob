package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiConstructor

@JsonSerializable
data class LoginRequest @KotshiConstructor constructor(
        var email: String? = null,
        var password: String? = null
) {

}