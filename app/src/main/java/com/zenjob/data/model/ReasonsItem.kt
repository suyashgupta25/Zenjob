package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ReasonsItem(
        val subReasons: List<Any?>? = null,
        val name: String? = null,
        val needsComment: Boolean? = null,
        val label: String? = null,
        val labelDe: String? = null
)
