package com.example.authform

import android.content.Context
import android.util.JsonReader
import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader

@Serializable
data class User(
    @SerialName(value = "idUsername") val username: String?,
    @SerialName(value = "idPassword") val password: String?
)

class UserStorage {
    constructor(fileName: String) {
        filePath = fileName;
    }

    fun ReadFromFile(context: Context) {
        val jsonString = context.assets.open(filePath).bufferedReader().use { it.readText() }

        Log.d("TEST", jsonString)
    }

    fun DoesUserExist(username: String, password: String): Boolean {
        return false;
    }

    private val filePath: String;
    private val users = arrayListOf<User>()
}