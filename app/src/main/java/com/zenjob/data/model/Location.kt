package com.zenjob.data.model

data class Location(
        val locationName: String? = null,
        val streetNumber: String? = null,
        val city: String? = null,
        val street: String? = null,
        val postalCode: String? = null,
        val district: String? = null,
        val locationLongitude: Double? = null,
        val locationLatitude: Double? = null,
        val supplementary: Any? = null,
        val locationSearchString: String? = null
)
