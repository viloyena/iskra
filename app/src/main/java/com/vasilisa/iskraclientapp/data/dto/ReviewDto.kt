package com.vasilisa.iskraclientapp.data.dto

data class ReviewDto(
    val authorId: String,
    val author: String,
    val rating: String,
    val comment: String?,
    val date: String
)
