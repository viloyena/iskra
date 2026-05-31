package com.vasilisa.hello.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vasilisa.hello.MainActivity
import com.vasilisa.hello.R
import com.vasilisa.hello.data.api.RetrofitClient
import com.vasilisa.hello.data.dto.LoginRequestDto
import com.vasilisa.hello.data.dto.LoginResponseDto
import com.vasilisa.hello.data.storage.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val tokenManager =
            TokenManager(this)

        val emailEdit =
            findViewById<EditText>(R.id.emailEdit)

        val passwordEdit =
            findViewById<EditText>(R.id.passwordEdit)

        val loginButton =
            findViewById<Button>(R.id.loginButton)

        val registerButton =
            findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {

            val email =
                emailEdit.text.toString().trim()

            val password =
                passwordEdit.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Заполните все поля",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val request =
                LoginRequestDto(
                    email = email,
                    password = password
                )

            RetrofitClient.create(this)
                .login(request)
                .enqueue(object : Callback<LoginResponseDto> {

                    override fun onResponse(
                        call: Call<LoginResponseDto>,
                        response: Response<LoginResponseDto>
                    ) {

                        if (response.isSuccessful) {

                            val token =
                                response.body()?.accessToken

                            if (token != null) {

                                tokenManager.saveToken(token)

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Вход выполнен",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                )

                                finish()

                            } else {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Токен не получен",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {

                            Toast.makeText(
                                this@LoginActivity,
                                "Неверный email или пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<LoginResponseDto>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Ошибка: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        registerButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}