package com.vasilisa.hello.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vasilisa.hello.R
import com.vasilisa.hello.data.api.RetrofitClient
import com.vasilisa.hello.data.dto.Gender
import com.vasilisa.hello.data.dto.RegisterRequestDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val fullNameEdit = findViewById<EditText>(R.id.fullnameEdit)
        val emailEdit = findViewById<EditText>(R.id.emailEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val phoneEdit = findViewById<EditText>(R.id.phoneEdit)
        val birthDateEdit = findViewById<EditText>(R.id.birthDateEdit)

        val genderSpinner =
            findViewById<Spinner>(R.id.genderSpinner)

        val genders = Gender.entries.map { it.title }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genders
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        genderSpinner.adapter = adapter

        val registerButton =
            findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {

            val selectedGender =
                Gender.entries[genderSpinner.selectedItemPosition]

            val dto = RegisterRequestDto(
                fullname = fullNameEdit.text.toString(),
                email = emailEdit.text.toString(),
                password = passwordEdit.text.toString(),
                phoneNumber = phoneEdit.text.toString(),
                birthDate = birthDateEdit.text.toString(),
                gender = selectedGender.toString()
            )

            RetrofitClient.create(this).register(dto)
                .enqueue(object : Callback<String> {

                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {

                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@RegisterActivity,
                                "Регистрация успешна",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
                                )
                            )

                            finish()

                        } else {

                            val errorBody = response.errorBody()?.string()

                            var message = "Ошибка регистрации"

                            try {

                                val json = JSONObject(errorBody!!)

                                val errors = json.getJSONObject("errors")

                                val keys = errors.keys()

                                val errorMessages = mutableListOf<String>()

                                while (keys.hasNext()) {

                                    val key = keys.next()

                                    val array = errors.getJSONArray(key)

                                    for (i in 0 until array.length()) {
                                        errorMessages.add(array.getString(i))
                                    }
                                }

                                message = errorMessages.joinToString("\n")

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            Toast.makeText(
                                this@RegisterActivity,
                                message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<String>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@RegisterActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}