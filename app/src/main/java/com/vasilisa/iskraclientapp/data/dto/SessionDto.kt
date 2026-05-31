package com.vasilisa.iskraclientapp.data.dto

data class SessionDto(
    val sessionId: String,
    val title: String,
    val description: String,
    val type: String,
    val durationMins: String,
    val startDate: String,
    val price: String,
    val instructor: InstructorDto,
    val bookingsCount: String
)