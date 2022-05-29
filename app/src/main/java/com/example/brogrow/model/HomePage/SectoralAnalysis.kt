package com.example.brogrow.model.HomePage

data class SectoralAnalysis(
    val name: String,
    val non_performing_sectors: List<NonPerformingSector>,
    val rating: Double,
    val remark: String,
    val top_performing_sectors: List<TopPerformingSector>
)