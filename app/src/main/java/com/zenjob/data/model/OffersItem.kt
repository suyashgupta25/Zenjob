package com.zenjob.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class OffersItem(
        val id: String? = null,
        val instructions: String? = null,
        val breakTypes: List<BreakTypesItem?>? = null,
        val earningTotal: String? = null,
        val minutesSum: String? = null,
        val companyName: String? = null,
        val description: String? = null,
        val title: String? = null,
        val hourSum: Any? = null,
        val companyLogoUrl: String? = null,
        val earningHourly: String? = null,
        val pricingTables: List<PricingTablesItem?>? = null,
        val shifts: List<ShiftsItem?>? = null,
        val location: Location? = null,
        val jobCategoryKey: String? = null
)
