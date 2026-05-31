package com.vasilisa.iskraclientapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.data.api.RetrofitClient
import com.vasilisa.iskraclientapp.data.dto.UserProfileDto
import com.vasilisa.iskraclientapp.data.storage.TokenManager
import com.vasilisa.iskraclientapp.ui.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager(requireContext())

        val fullnameText =
            view.findViewById<TextView>(R.id.fullnameText)

        val emailText =
            view.findViewById<TextView>(R.id.emailText)

        val phoneText =
            view.findViewById<TextView>(R.id.phoneText)

        val birthDateText =
            view.findViewById<TextView>(R.id.birthDateText)

        val logoutButton =
            view.findViewById<Button>(R.id.logoutButton)

        loadProfile(
            fullnameText,
            emailText,
            phoneText,
            birthDateText
        )

        logoutButton.setOnClickListener {

            tokenManager.clearToken()

            val intent =
                Intent(requireContext(), LoginActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            requireActivity().finish()
        }
    }

    private fun loadProfile(
        fullnameText: TextView,
        emailText: TextView,
        phoneText: TextView,
        birthDateText: TextView
    ) {

        RetrofitClient.create(requireContext())
            .getProfile()
            .enqueue(object : Callback<UserProfileDto> {

                override fun onResponse(
                    call: Call<UserProfileDto>,
                    response: Response<UserProfileDto>
                ) {

                    if (response.isSuccessful) {

                        val user = response.body()

                        fullnameText.text =
                            user?.fullname ?: ""

                        emailText.text =
                            user?.email ?: ""

                        phoneText.text =
                            user?.phoneNumber ?: ""

                        birthDateText.text =
                            user?.birthDate ?: ""

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "Ошибка загрузки профиля",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<UserProfileDto>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        requireContext(),
                        "Ошибка сети: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}