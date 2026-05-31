package com.vasilisa.iskraclientapp.data.dto

enum class Gender(val value: Int, val title: String) {
    UNKNOWN(0, "Не указан"),
    MALE(1, "Мужской"),
    FEMALE(2, "Женский")
}