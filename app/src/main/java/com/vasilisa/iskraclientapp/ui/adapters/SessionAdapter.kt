package com.vasilisa.iskraclientapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.data.dto.SessionDto

class SessionAdapter(
    private val sessions: List<SessionDto>,
    private val buttonText: String,
    private val onActionClick: (SessionDto) -> Unit
) : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleText: TextView = view.findViewById(R.id.titleText)
        val instructorText: TextView = view.findViewById(R.id.instructorText)
        val dateText: TextView = view.findViewById(R.id.dateText)
        val actionButton: Button = view.findViewById(R.id.bookButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session, parent, false)

        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {

        val session = sessions[position]

        holder.titleText.text = session.title
        holder.instructorText.text = session.instructor.name
        holder.dateText.text = session.startDate

        holder.actionButton.text = buttonText

        holder.actionButton.setOnClickListener {
            onActionClick(session)
        }
    }

    override fun getItemCount() = sessions.size
}