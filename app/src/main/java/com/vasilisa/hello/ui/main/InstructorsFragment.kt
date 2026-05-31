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
import com.vasilisa.hello.data.api.ServerApi
import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.SessionDto
import com.vasilisa.hello.ui.adapters.InstructorAdapter
import com.vasilisa.hello.ui.adapters.SessionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstructorsFragment : Fragment(R.layout.fragment_instructors) {
    lateinit var recyclerViewInstructor: RecyclerView;
    var InstructorList: MutableList<InstructorDto> = mutableListOf();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_instructors,
            container,
            false
        )

        recyclerViewInstructor =
            view.findViewById<RecyclerView>(
                R.id.rvInstructor
            )

        recyclerViewInstructor.layoutManager =
            LinearLayoutManager(requireContext())

        getInstructors();


        return view
    }

    fun getInstructors() {
        val request = RetrofitClient.create(requireContext()).getInstructors()
        request.enqueue(object : Callback<List<InstructorDto>> {
            override fun onResponse(
                call: Call<List<InstructorDto>>,
                response: Response<List<InstructorDto>>
            ) {
                val instructor: List<InstructorDto> = (response.body() ?: mutableListOf())
                instructor.forEach { pr ->
                    Log.d(
                        "WWW",
                        pr.name.toString() + " "
                                + pr.experienceYears.toString() + " "
                                + pr.rating.toString()
                    );

                    InstructorList.add(pr)
                }
                val adapter = InstructorAdapter(InstructorList)
                recyclerViewInstructor.adapter = adapter
            }

            override fun onFailure(call: Call<List<InstructorDto>>, t: Throwable) {
                Log.d("WWW", "Error:\n" + t.message)
            }
        })

    }
}