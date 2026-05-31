package com.vasilisa.iskraclientapp.data.dto

data class CreateReviewDto(
    val instructorId: String,
    val rating: Int,
    val comment: String?
)