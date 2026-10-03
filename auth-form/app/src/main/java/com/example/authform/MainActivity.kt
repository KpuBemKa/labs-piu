package com.example.authform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.authform.ui.theme.AuthFormTheme

val g_userStorage = UserStorage("users.json")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userStorage = UserStorage("users.json")
            userStorage.ReadFromFile(baseContext)
            Json

            val usernameTextState = rememberTextFieldState()
            val passwordTextState = rememberTextFieldState()
            AuthFormTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Credentials(
                            usernameTextState,
                            passwordTextState,
                            modifier = Modifier.padding(innerPadding)
                        )
                        Button(
                            onClick = fun() {
                                onLogin(
                                    usernameTextState.text.toString(),
                                    passwordTextState.text.toString()
                                )
                            },
                            modifier = Modifier.padding(innerPadding)
                        ) { Text("Log In") }
                    }
                }
            }
        }
    }

    private fun onLogin(
        username: CharSequence,
        password: CharSequence,
    ) {
//        userStorage.authenticateUser(username.toString(), password.toString(), baseContext)
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private val userStorage = UserStorage("users.json")
}

@Composable
fun Credentials(
    usernameState: TextFieldState,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier
) {
    Column {
        TextField(
            state = usernameState, modifier = modifier, label = { Text("Username") })
        SecureTextField(
            state = passwordState, label = { Text("Password") })
    }
}

fun onLogin(
    username: CharSequence,
    password: CharSequence,
) {
    Log.d("TEST",  username.toString())
    Log.d("TEST", password.toString())
}