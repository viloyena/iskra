package com.vasilisa.iskraclientapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.data.api.RetrofitClient
import com.vasilisa.iskraclientapp.data.dto.CreateReviewDto
import com.vasilisa.iskraclientapp.data.dto.InstructorDto
import com.vasilisa.iskraclientapp.data.dto.ReviewDto
import com.vasilisa.iskraclientapp.ui.adapters.ReviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstructorProfileFragment : Fragment(R.layout.fragment_instructor_profile) {

    private lateinit var instructorId: String

    private lateinit var adapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        instructorId = arguments?.getString("instructorId") ?: return

        val nameText =
            view.findViewById<TextView>(R.id.profileNameText)

        val expText =
            view.findViewById<TextView>(R.id.profileExperienceText)

        val ratingText =
            view.findViewById<TextView>(R.id.profileRatingText)

        val recycler = view.findViewById<RecyclerView>(R.id.reviewsRv)
        val commentInput = view.findViewById<EditText>(R.id.commentInput)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val sendBtn = view.findViewById<Button>(R.id.sendBtn)
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ReviewAdapter(emptyList())
        recycler.adapter = adapter

        loadInstructor(nameText, expText, ratingText)
        loadReviews()

        sendBtn.setOnClickListener {
            sendReview(commentInput, ratingBar)
        }

        deleteBtn.setOnClickListener {

            RetrofitClient.create(requireContext())
                .deleteReview(instructorId)
                .enqueue(object : Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {

                        loadReviews()
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {}
                })
        }
    }

    private fun loadInstructor(
        name: TextView,
        exp: TextView,
        rating: TextView
    ) {

        RetrofitClient.create(requireContext())
            .getInstructor(instructorId)
            .enqueue(object : Callback<InstructorDto> {

                override fun onResponse(
                    call: Call<InstructorDto>,
                    response: Response<InstructorDto>
                ) {

                    Log.d("WWW", "CODE = ${response.code()}")

                    if (!response.isSuccessful) {
                        return
                    }

                    val data = response.body()

                    Log.d("WWW", "DATA = $data")

                    if (data == null) {
                        return
                    }

                    name.text = data.fullname
                    exp.text = "Опыт: ${data.experienceYears}"
                    rating.text = "Рейтинг: ${data.rating}"
                }

                override fun onFailure(
                    call: Call<InstructorDto>,
                    t: Throwable
                ) {
                    Log.d("WWW", t.message.toString())
                }
            })
    }

    private fun loadReviews() {

        RetrofitClient.create(requireContext())
            .getReviews(instructorId)
            .enqueue(object : Callback<List<ReviewDto>> {

                override fun onResponse(
                    call: Call<List<ReviewDto>>,
                    response: Response<List<ReviewDto>>
                ) {

                    val reviews = response.body() ?: emptyList()

                    Log.d("WWW", reviews.toString())

                    adapter.update(reviews)
                }

                override fun onFailure(
                    call: Call<List<ReviewDto>>,
                    t: Throwable
                ) {}
            })
    }

    private fun sendReview(comment: EditText, ratingBar: RatingBar) {

        val dto = CreateReviewDto(
            instructorId = instructorId,
            rating = ratingBar.rating.toInt(),
            comment = comment.text.toString()
        )

        RetrofitClient.create(requireContext())
            .postReview(dto)
            .enqueue(object : Callback<Void> {

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    comment.text.clear()
                    ratingBar.rating = 0f
                    loadReviews() // обновляем список
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {}
            })
    }
}