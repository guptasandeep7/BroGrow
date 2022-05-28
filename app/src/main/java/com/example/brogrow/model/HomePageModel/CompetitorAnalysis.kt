package com.example.brogrow.model.HomePageModel

data class CompetitorAnalysis(
    val competitors: List<Competitor>,
    val name: String,
    val rating: Double,
    val remarks: String
)