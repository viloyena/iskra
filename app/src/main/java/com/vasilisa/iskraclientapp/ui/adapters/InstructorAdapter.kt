package com.vasilisa.iskraclientapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.data.dto.InstructorDto

class InstructorAdapter(
    private val instructors: List<InstructorDto>,
    private val onClick: (InstructorDto) -> Unit
) : RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    class InstructorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameText: TextView =
            view.findViewById(R.id.nameText)

        val experienceText: TextView =
            view.findViewById(R.id.experienceText)

        val ratingText: TextView =
            view.findViewById(R.id.ratingText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InstructorViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_instructor,
                parent,
                false
            )

        return InstructorViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val instructor = instructors[position]

        holder.nameText.text = instructor.name
        holder.experienceText.text = "Опыт: ${instructor.experienceYears}"
        holder.ratingText.text = "Рейтинг: ${instructor.rating}"

        holder.itemView.setOnClickListener {
            onClick(instructor)
        }
    }

    override fun getItemCount(): Int {
        return instructors.size
    }
}