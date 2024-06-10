package ru.yarsu.classes

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Projects(
    val idMan: UUID,
    val id: UUID,
    val name: String,
    val contrib: MutableList<Contribution>,
    val company: Company,
    val targetSum: Long,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime,
    val description: String,
)
{
    fun getDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        return date.format(formatter)
    }
    fun getNameProject(pr: Projects): String {
        val getName = pr.name
        return getName.replace("\\s".toRegex(), "")
    }
}

