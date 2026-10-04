package com.example.authform

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        username = intent.getStringExtra("username").orEmpty()

        val books = bookStorage.getAll(this)
        val listView = findViewById<ListView>(R.id.booksListView)
        listView.adapter = BookAdapter(books)

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, BookDetailsActivity::class.java)
            intent.putExtra("book_id", books[position].id)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            showBookPopup(books[position])
            true
        }

        findViewById<Button>(R.id.detailsSignOutButton).setOnClickListener {
            showSignOutDialog()
        }

        onBackPressedDispatcher.addCallback(this) {
            showExitDialog()
        }
    }

    private fun showBookPopup(book: Book) {
        AlertDialog.Builder(this)
            .setTitle(book.title)
            .setPositiveButton("Adauga") { _, _ ->
                Toast.makeText(this, "Cartea a fost adaugata", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Sterge") { _, _ ->
                Toast.makeText(this, "Cartea a fost stearsa", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setMessage("Doriti sa iesiti din aplicatie?")
            .setPositiveButton("Da") { _, _ -> finishAffinity() }
            .setNegativeButton("Nu", null)
            .show()
    }

    private fun showSignOutDialog() {
        AlertDialog.Builder(this)
            .setMessage("Doriti sa va delogati?")
            .setPositiveButton("Da") { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            .setNegativeButton("Nu", null)
            .show()
    }

    private inner class BookAdapter(books: List<Book>) :
        ArrayAdapter<Book>(this, R.layout.book_list_item, books) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView
                ?: LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false)
            val book = getItem(position)!!

            view.findViewById<TextView>(R.id.itemTitleText).text = book.title
            view.findViewById<TextView>(R.id.itemAuthorText).text = "${book.author}, ${book.year}"

            return view
        }
    }

    private var username = ""
    private val bookStorage = BookStorage("books.json")
}
