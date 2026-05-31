package com.vasilisa.iskraclientapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vasilisa.iskraclientapp.ui.main.HomeFragment
import com.vasilisa.iskraclientapp.ui.main.InstructorsFragment
import com.vasilisa.iskraclientapp.ui.main.ProfileFragment
import com.vasilisa.iskraclientapp.ui.main.ScheduleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Добавляем отступы под системные панели
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Первый экран при запуске приложения
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Обработка нажатий BottomNavigation
        bottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.menu_schedule -> {
                    loadFragment(ScheduleFragment())
                    true
                }

                R.id.menu_instructors -> {
                    loadFragment(InstructorsFragment())
                    true
                }

                R.id.menu_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    // Замена текущего фрагмента
    private fun loadFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}