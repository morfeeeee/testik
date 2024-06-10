package ru.yarsu.classes

data class Permissions (
    val canAddProject: Boolean = false,
    val canEditProject: Boolean = false,
    val canDeleteProject: Boolean = false,
    val canAddContrib: Boolean = false,
    val canEditContrib: Boolean = false,
    val canDeleteContrib: Boolean = false,
    val canListContrib: Boolean = false,
    val canListUsers: Boolean = false,
    val canAddUser: Boolean = false,
)