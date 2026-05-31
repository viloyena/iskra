package com.vasilisa.iskraclientapp.data.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vasilisa.iskraclientapp.R
import com.vasilisa.iskraclientapp.ui.main.HomeFragment
import com.vasilisa.iskraclientapp.ui.main.InstructorsFragment
import com.vasilisa.iskraclientapp.ui.main.ProfileFragment
import com.vasilisa.iskraclientapp.ui.main.ScheduleFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // стартовый экран
        openFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.menu_home -> openFragment(HomeFragment())

                R.id.menu_schedule -> openFragment(ScheduleFragment())

                R.id.menu_instructors -> openFragment(InstructorsFragment())

                R.id.menu_profile -> openFragment(ProfileFragment())
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}