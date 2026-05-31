package com.vasilisa.hello.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vasilisa.hello.R
import com.vasilisa.hello.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val loginButton =
            findViewById<Button>(R.id.loginButton)

        val registerButton =
            findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {

            val intent =
                Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        registerButton.setOnClickListener {

            val intent =
                Intent(this, RegisterActivity::class.java)

            startActivity(intent)
        }
    }
}