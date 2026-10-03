package com.example.authform

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerialName(value = "username") val username: String,
    @SerialName(value = "password") val password: String?
)

class UserStorage {
    constructor(fileName: String) {
        filePath = fileName;
    }

    fun ReadFromFile(context: Context) {
        val jsonString = context.assets.open(filePath).bufferedReader().use { it.readText() }
        users.addAll(parseJsonToModel(jsonString))
    }

    fun DoesUserExist(username: String, password: String): Boolean {
        return users.find { it.username == username && it.password == password } != null
    }

    private fun parseJsonToModel(jsonString: String): List<User> {
        try {
            val sType = object : TypeToken<List<User>>() {}.type
            return Gson().fromJson<List<User>>(jsonString, sType)
        } catch (ex: Exception) {
            return emptyList()
        }
    }

    private val filePath: String;
    private val users = arrayListOf<User>()
}