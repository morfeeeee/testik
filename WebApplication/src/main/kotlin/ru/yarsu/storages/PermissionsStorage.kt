package ru.yarsu.storages

import ru.yarsu.classes.Permissions


class PermissionsStorage() {
    val permissionsMap = mutableMapOf<String, Permissions>()
    init {
        permissionsMap["admin"] = Permissions(
            canAddProject = true,
            canEditProject = true,
            canDeleteProject = true,
            canAddContrib = true,
            canEditContrib = true,
            canDeleteContrib = true,
            canListContrib = true,
            canListUsers = true,
            canAddUser = true
        )
        permissionsMap["holderOfCompany"] = Permissions(
            canAddProject = true,
            canEditProject = true,
            canDeleteProject = true,
            canAddContrib = true,
            canEditContrib = true,
            canDeleteContrib = true,
            canListContrib = true,
        )
        permissionsMap["investor"] = Permissions(
            canAddProject = true,
            canAddContrib = true,
            canEditContrib = true,
            canDeleteContrib = true,
            canListContrib = true,
        )
        permissionsMap["guest"] = Permissions()
    }
    fun getByKey(key: String): Permissions? {
        return permissionsMap[key]
    }
}