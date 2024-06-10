package ru.yarsu.storages

import java.security.SecureRandom
import java.util.UUID

class SaltStorage(val idSaltMap: Map<UUID, String>) {
    private val saltMap = mutableMapOf<UUID, String>()
    init {
        saltMap.putAll(idSaltMap)
    }
    fun getAllSalt(): Map<UUID, String> {
        return saltMap
    }
    fun generateSalt(length: Int = 100): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/"
        val random = SecureRandom()
        val salt = StringBuilder(length)
        for (i in 0 until length) {
            val randomIndex = random.nextInt(charset.length)
            salt.append(charset[randomIndex])
        }
        return salt.toString()
    }

    fun addSalt(idUser: UUID){
        saltMap[idUser] = generateSalt()
    }

    fun getSaltById(idUser: UUID): String {
        return saltMap[idUser].toString()
    }
}