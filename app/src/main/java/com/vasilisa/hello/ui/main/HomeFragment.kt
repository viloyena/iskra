package com.vasilisa.hello.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.R
import com.vasilisa.hello.data.api.RetrofitClient
import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.SessionDto
import com.vasilisa.hello.ui.adapters.SessionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {
    var SessionList: MutableList<SessionDto> = mutableListOf();
    lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        recyclerView =
            view.findViewById<RecyclerView>(
                R.id.sessionRecycler
            )

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        getSessions()

        return view
    }

    fun getSessions() {
        val request = RetrofitClient.create(requireContext()).getUserBookings() //создание, но не выполнение!
        request.enqueue(object : Callback<List<SessionDto>> {
            override fun onResponse(
                call: Call<List<SessionDto>>,
                response: Response<List<SessionDto>>
            ) {
                val instructor: List<SessionDto> = (response.body() ?: mutableListOf())
                instructor.forEach { pr ->
                    Log.d(
                        "WWW",
                        pr.title.toString() + " "
                                + pr.description.toString() + " "
                                + pr.type.toString() + " "
                                + pr.durationMins.toString() + " "
                                + pr.startDate.toString()
                                + pr.price.toString()
                                + pr.instructor.toString()
                                + pr.bookingsCount.toString()
                    )
                    SessionList.add(pr)
                }
                // 4. Инициализация и подключение адаптера
                val adapter = SessionAdapter(SessionList) { session ->

                    // действие при клике
                    val context = requireContext()

                    RetrofitClient.create(context)
                        .bookSession(session.sessionId)
                        .enqueue(object : retrofit2.Callback<Void> {

                            override fun onResponse(
                                call: retrofit2.Call<Void>,
                                response: retrofit2.Response<Void>
                            ) {

                                if (response.isSuccessful) {

                                    android.widget.Toast.makeText(
                                        context,
                                        "Запись успешна",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    android.widget.Toast.makeText(
                                        context,
                                        "Ошибка: ${response.code()}",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(
                                call: retrofit2.Call<Void>,
                                t: Throwable
                            ) {

                                android.widget.Toast.makeText(
                                    context,
                                    "Ошибка сети",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<SessionDto>>, t: Throwable) {
                Log.d("WWW", "Error:\n" + t.message)
            }
        })
    }
}