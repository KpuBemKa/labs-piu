package com.example.authform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val loginInput = findViewById<TextInputLayout>(R.id.loginInputLayout)
        val passwordInput = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            onLoginClick(
                loginInput, passwordInput
            )
        }
    }

    private fun onLoginClick(
        usernameField: TextInputLayout,
        passwordField: TextInputLayout,
    ) {
        usernameField.error = ""
        passwordField.error = ""

        var validationPassed = true

        val usernameString = usernameField.editText?.text?.toString().orEmpty()
        val passwordString = passwordField.editText?.text?.toString().orEmpty()

        if (usernameString.isBlank()) {
            usernameField.error = "Login-ul nu poate fi gol."
            validationPassed = false
        }

        if (passwordString.isBlank()) {
            passwordField.error = "Parola nu poate fi goala."
            validationPassed = false
        }

        if (!validationPassed) {
            return
        }

        if (!userStorage.authenticateUser(usernameString, passwordString, baseContext)) {
            findViewById<TextView>(R.id.loginResultText).visibility = View.VISIBLE
            return
        }

        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("username", usernameString)
        startActivity(intent)
        finish()
    }

    private val userStorage = UserStorage("users.json")
}