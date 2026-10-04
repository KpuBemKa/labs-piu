package com.example.authform

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.FileNotFoundException

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val genre: String,
    val pages: Int,
    val description: String
)

class BookStorage(private val filePath: String) {

    fun getAll(context: Context): List<Book> {
        if (books_.isEmpty()) {
            readFromFile(context)
        }
        return books_
    }

    fun getById(id: Int, context: Context): Book? {
        return getAll(context).find { it.id == id }
    }

    private fun readFromFile(context: Context) {
        try {
            val jsonObject = JSONObject(context.assets.open(filePath).bufferedReader().use { it.readText() })
            val books = jsonObject.getJSONArray("books")

            for (i in 0 until books.length()) {
                val item = books.getJSONObject(i)
                books_.add(
                    Book(
                        item.getInt("id"),
                        item.getString("title"),
                        item.getString("author"),
                        item.getInt("year"),
                        item.getString("genre"),
                        item.getInt("pages"),
                        item.getString("description")
                    )
                )
            }
        } catch (e: FileNotFoundException) {
            Log.e("TEST", e.toString())
        } catch (e: JSONException) {
            Log.e("TEST", e.toString())
        }
    }

    private val books_ = arrayListOf<Book>()
}

object VisitCounter {
    private val visits = HashMap<String, Int>()

    fun addVisit(username: String, bookId: Int): Int {
        val key = "$username-$bookId"
        val count = (visits[key] ?: 0) + 1
        visits[key] = count
        return count
    }
}
