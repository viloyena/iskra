package com.vasilisa.hello.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.R
import com.vasilisa.hello.data.api.RetrofitClient
import com.vasilisa.hello.data.dto.SessionDto
import com.vasilisa.hello.ui.adapters.SessionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val sessionList = mutableListOf<SessionDto>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SessionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.sessionRecycler)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        adapter = SessionAdapter(
            sessionList,
            buttonText = "Отменить запись"
        ) { session ->
            cancelBooking(session.sessionId)
        }

        recyclerView.adapter = adapter

        getSessions()
    }

    private fun getSessions() {

        RetrofitClient.create(requireContext())
            .getUserBookings()
            .enqueue(object : Callback<List<SessionDto>> {

                override fun onResponse(
                    call: Call<List<SessionDto>>,
                    response: Response<List<SessionDto>>
                ) {

                    val data = response.body() ?: emptyList()

                    sessionList.clear()
                    sessionList.addAll(data)

                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<SessionDto>>, t: Throwable) {

                    Log.d("WWW", "Error: ${t.message}")
                }
            })
    }

    private fun cancelBooking(sessionId: String) {

        RetrofitClient.create(requireContext())
            .cancelBooking(sessionId)
            .enqueue(object : Callback<Void> {

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            requireContext(),
                            "Запись отменена",
                            Toast.LENGTH_SHORT
                        ).show()

                        getSessions() // обновляем список

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "Ошибка: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                    Toast.makeText(
                        requireContext(),
                        "Ошибка сети: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}