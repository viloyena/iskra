package com.vasilisa.hello.data.dto

data class UserProfileDto(
    val id: String,
    val fullname: String,
    val email: String,
    val phoneNumber: String?,
    val gender: Int,
    val birthDate: String
)