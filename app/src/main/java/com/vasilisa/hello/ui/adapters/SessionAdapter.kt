package com.vasilisa.hello.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.R
import com.vasilisa.hello.data.dto.SessionDto

class SessionAdapter(
    private val sessions: List<SessionDto>
) : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleText: TextView =
            view.findViewById(R.id.titleText)

        val instructorText: TextView =
            view.findViewById(R.id.instructorText)

        val dateText: TextView =
            view.findViewById(R.id.dateText)

        val actionButton: Button =
            view.findViewById(R.id.actionButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_session,
                parent,
                false
            )

        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SessionViewHolder,
        position: Int
    ) {

        val session = sessions[position]

        holder.titleText.text =
            session.title

        holder.instructorText.text =
            session.instructor.name

        holder.dateText.text =
            session.startDate

        holder.actionButton.setOnClickListener { view ->
            // БЕРЕМ КОНТЕКСТ ИЗ view.context
            val context = view.context

            Toast.makeText(context, "Нажат элемент: ${session.title}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return sessions.size
    }
}