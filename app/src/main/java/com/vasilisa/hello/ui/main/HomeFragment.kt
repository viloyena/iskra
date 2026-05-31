package com.vasilisa.hello.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.R
import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.SessionDto
import com.vasilisa.hello.ui.adapters.SessionAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

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

        val recyclerView =
            view.findViewById<RecyclerView>(
                R.id.sessionRecycler
            )

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        val sessions = listOf(

            SessionDto(
                sessionId = "1",
                title = "Скалолазание",
                description = "Тренировка для новичков",
                type = "0",
                durationMins = "90",
                startDate = "28.05.2026 18:00",
                price = "1500",
                instructor = InstructorDto(
                    instructorId = "1",
                    name = "Анна",
                    experienceYears = "5",
                    rating = "4.9"
                ),
                bookingsCount = "10"
            ),

            SessionDto(
                sessionId = "2",
                title = "Боулдеринг",
                description = "Средний уровень",
                type = "Individual",
                durationMins = "60",
                startDate = "29.05.2026 20:00",
                price = "2000",
                instructor = InstructorDto(
                    instructorId = "2",
                    name = "Иван",
                    experienceYears = "7",
                    rating = "5.0"
                ),
                bookingsCount = "5"
            )
        )

        recyclerView.adapter =
            SessionAdapter(sessions)

        return view
    }
}