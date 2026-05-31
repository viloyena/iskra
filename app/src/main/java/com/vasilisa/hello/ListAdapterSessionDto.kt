package com.vasilisa.hello

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilisa.hello.data.dto.SessionDto

class ListAdapterSessionDto(private val items: List<SessionDto>) :
    RecyclerView.Adapter<ListAdapterSessionDto.ViewHolder>() {

    // Класс для хранения ссылок на элементы разметки одной строки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewItem)
    }

    // Создает новый внешний вид для строки списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    // Наполняет созданную строку конкретными данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].title
    }

    // Возвращает общее количество элементов списка
    override fun getItemCount(): Int = items.size
}