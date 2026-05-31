package com.vasilisa.hello.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vasilisa.hello.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // профиль пользователя
        // кнопка logout:
        // удалить JWT и вернуть на LoginActivity
    }
}