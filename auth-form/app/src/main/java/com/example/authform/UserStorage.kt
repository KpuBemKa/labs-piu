package com.example.authform

import android.content.Context
import android.content.res.Resources
import android.util.JsonReader
import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.json.JSONException
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader

data class User(
    val username: String,
    val password: String
)

class UserStorage {
    constructor(fileName: String) {
        filePath = fileName;
    }

    fun authenticateUser(username: String, password: String, context: Context): Boolean {
        if (users_.isEmpty()) {
            this.readFromFile(context)
        }

        return (users_.find {it.username == username && it.password == password} != null)
    }

    private fun readFromFile(context: Context)  {
        try {
            val jsonObject = JSONObject(context.assets.open(filePath).bufferedReader().use { it.readText() })
            val users = jsonObject.getJSONArray("users")

            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                users_.add(User(item.getString("username"), item.getString("password")))
            }

        } catch (e: FileNotFoundException) {
            Log.e("TEST", e.toString())
        }
        catch (e: JSONException) {
            throw Resources.NotFoundException("No users have been found in the `users.json` file.")
        }
    }


    private val filePath: String
    private val users_ = arrayListOf<User>()
}