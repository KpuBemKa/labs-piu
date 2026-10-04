package com.example.authform

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class BookDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_details_page)

        val bookId = intent.getIntExtra("book_id", -1)
        val username = intent.getStringExtra("username").orEmpty()

        val book = bookStorage.getById(bookId, this)
        if (book == null) {
            Toast.makeText(this, "Cartea nu a fost gasita", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.detailsTitleText).text = book.title
        findViewById<TextView>(R.id.detailsAuthorText).text = book.author
        findViewById<TextView>(R.id.detailsYearText).text = "An: ${book.year}"
        findViewById<TextView>(R.id.detailsGenreText).text = "Gen: ${book.genre}"
        findViewById<TextView>(R.id.detailsPagesText).text = "Pagini: ${book.pages}"
        findViewById<TextView>(R.id.detailsDescriptionText).text = book.description

        val visits = VisitCounter.addVisit(username, book.id)
        findViewById<TextView>(R.id.detailsVisitsText).text =
            "Ati vizitat aceasta carte de $visits ori"


        findViewById<Button>(R.id.detailsSignOutButton).setOnClickListener {
            showSignOutDialog()
        }
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

    private val bookStorage = BookStorage("books.json")
}
