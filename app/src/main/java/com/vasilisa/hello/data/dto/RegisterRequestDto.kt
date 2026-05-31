package com.vasilisa.hello.data.dto

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val fullname: String,
    val phoneNumber: String,
    val birthDate: String,
    val gender: Int
)