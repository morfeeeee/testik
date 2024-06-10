package ru.yarsu.classes

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Contribution(
    val idUSER: UUID,
    val date: LocalDate,
    val sum: Double,
    val id: UUID,
)
{
    fun getDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date.format(formatter)
    }
}