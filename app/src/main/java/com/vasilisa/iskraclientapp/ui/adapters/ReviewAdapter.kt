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

        val userName: TextView =
            view.findViewById(R.id.userNameText)

        val date: TextView =
            view.findViewById(R.id.dateText)

        val comment: TextView =
            view.findViewById(R.id.commentText)

        val rating: TextView =
            view.findViewById(R.id.ratingText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewVH {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_review,
                parent,
                false
            )

        return ReviewVH(view)
    }

    override fun onBindViewHolder(
        holder: ReviewVH,
        position: Int
    ) {

        val review = reviews[position]

        holder.userName.text = review.author

        holder.rating.text =
            "Рейтинг: ${review.rating}/5"

        holder.date.text = review.date

        holder.comment.text =
            review.comment ?: "Без комментария"
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun update(newList: List<ReviewDto>) {
        reviews = newList
        notifyDataSetChanged()
    }
}