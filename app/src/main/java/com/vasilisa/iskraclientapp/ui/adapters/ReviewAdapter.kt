package com.vasilisa.iskraclientapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.data.dto.ReviewDto

class ReviewAdapter(
    private var reviews: List<ReviewDto>
) : RecyclerView.Adapter<ReviewAdapter.ReviewVH>() {

    class ReviewVH(view: View) : RecyclerView.ViewHolder(view) {
        val comment: TextView = view.findViewById(R.id.commentText)
        val rating: TextView = view.findViewById(R.id.ratingText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewVH(view)
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        val review = reviews[position]

        holder.comment.text = review.comment ?: "Без комментария"
        holder.rating.text = "⭐ ${review.rating}"
    }

    override fun getItemCount() = reviews.size

    fun update(newList: List<ReviewDto>) {
        reviews = newList
        notifyDataSetChanged()
    }
}