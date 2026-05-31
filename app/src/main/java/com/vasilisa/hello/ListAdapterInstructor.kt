package com.vasilisa.hello

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.SessionDto

class ListAdapterInstructor(private val items: List<InstructorDto>) :
    RecyclerView.Adapter<ListAdapterInstructor.ViewHolder>() {

    // Класс для хранения ссылок на элементы разметки одной строки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNameText: TextView = view.findViewById(R.id.nameText)

    }

    // Создает новый внешний вид для строки списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_instructor, parent, false)
        return ViewHolder(view)
    }

    // Наполняет созданную строку конкретными данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewNameText.text = items[position].name
    }

    // Возвращает общее количество элементов списка
    override fun getItemCount(): Int = items.size
}