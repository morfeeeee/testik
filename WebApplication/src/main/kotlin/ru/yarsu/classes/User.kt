package ru.yarsu.classes

import java.time.LocalDateTime
import java.util.UUID

class User(
    val nameUser: String,
    val password: String,
    val date: LocalDateTime,
    val idUser: UUID,
    var role: String
)