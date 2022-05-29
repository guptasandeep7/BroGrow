package com.example.brogrow.model.HomePage

data class CompetitorAnalysis(
    val competitors: List<Competitor>,
    val name: String,
    val rating: Double,
    val remarks: String
)