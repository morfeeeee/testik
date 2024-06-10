package ru.yarsu.functions

import java.util.UUID

fun generateID(): String {
    return UUID.randomUUID().toString()
}