package com.vasilisa.hello.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.vasilisa.hello.R
import com.vasilisa.hello.data.dto.Gender

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

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

            // После регистрации возвращаем на логин
            val intent =
                Intent(this, LoginActivity::class.java)

            startActivity(intent)

            finish()
        }
    }
}