package ru.yarsu.storages

import ru.yarsu.classes.User
import java.util.UUID

class UserStorage(val userList: List<User>)
{
    private val userMap = mutableMapOf<UUID, User>()
    init {
        userList.forEach { addUser(it) }
    }

    fun getAllUsers(): List<User> {
        return userMap.values.toList()
    }

    fun addUser(user: User) {
        userMap[user.idUser] = user
    }

    fun getIdUserByLogin(login: String?): UUID? {
        for (i in getAllUsers()){
            if (i.nameUser == login) return i.idUser
        }
        return null
    }

    fun truePassword(idUser: UUID, password: String): Boolean {
        for (i in getAllUsers()) {
            if (i.idUser == idUser && i.password == password) return true
        }
        return false
    }

    fun getUserById(idUser: UUID): User? {
        for (i in getAllUsers()){
            if (i.idUser == idUser) return i
        }
        return null
    }
    fun userDelete(user: User) {
        if(!userMap.remove(user.idUser, user)){
            return
        }
    }
    fun editRoleUser(user: User, role: String){
        val newUser = User(user.nameUser, user.password, user.date, user.idUser, role)
        userMap[user.idUser] = newUser
    }
}