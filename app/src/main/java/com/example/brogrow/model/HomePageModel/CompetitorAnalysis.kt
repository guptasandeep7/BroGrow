package com.example.brogrow.model.HomePageModel

data class CompetitorAnalysis(
    val competitors: List<String>,
    val name: String,
    val rating: Double,
    val remarks: String
)