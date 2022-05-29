package com.example.brogrow.model.HomePage

data class SectoralAnalysis(
    val name: String,
    val rating: Double,
    val remark: String,
    val sectors: List<String>
)