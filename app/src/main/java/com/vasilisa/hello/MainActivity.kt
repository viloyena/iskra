package com.vasilisa.hello

import com.vasilisa.hello.data.api.ServerApi
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vasilisa.hello.ui.adapters.InstructorAdapter
import com.vasilisa.hello.ui.adapters.SessionAdapter
import com.vasilisa.hello.ui.main.HomeFragment
import com.vasilisa.hello.ui.main.InstructorsFragment
import com.vasilisa.hello.ui.main.ProfileFragment
import com.vasilisa.hello.ui.main.ScheduleFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var SessionList: MutableList<SessionDto> = mutableListOf();
    var InstructorList: MutableList<InstructorDto> = mutableListOf();
    lateinit var recyclerView: RecyclerView;
    lateinit var recyclerViewInstructor: RecyclerView;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

            /*findViewById<Button>(R.id.btnLogin).setOnClickListener {
            login()
        }*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       // getSchedule();
       // getInstructors();

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Отображаем первый экран при старте (проверка на null защищает от наложения при повороте)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Обработка кликов по меню
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



        // 2. Поиск RecyclerView в разметке
        /*recyclerView = findViewById(R.id.rvSessions)
// 3. Установка менеджера компоновки (делает список вертикальным)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 2. Поиск RecyclerView в разметке
        recyclerViewInstructor = findViewById(R.id.rvInstructor)
// 3. Установка менеджера компоновки (делает список вертикальным)
        recyclerViewInstructor.layoutManager = LinearLayoutManager(this)*/

    }

    // Универсальный метод для замены фрагмента в FrameLayout
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }


    fun login() {

        val request = RetrofitClient.api.login(
            LoginRequestDto(
                email = "test@test.com",
                password = "123456"
            )
        )

        request.enqueue(object : Callback<LoginResponseDto> {

            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {

                val token = response.body()?.accessToken

                RetrofitClient.jwtToken = token

                Log.d("JWT", token ?: "TOKEN NULL")

                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(
                call: Call<LoginResponseDto>,
                t: Throwable
            ) {
                Log.d("JWT", t.message ?: "ERROR")
            }
        })
    }

    fun getInstructors() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:5246/") //адрес всегда должен заканчиваться на /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serverApi: ServerApi = retrofit.create(ServerApi::class.java)
        val request = serverApi.getInstructors() //создание, но не выполнение!
        request.enqueue(object : Callback<List<InstructorDto>> {
            override fun onResponse(call: Call<List<InstructorDto>>, response: Response<List<InstructorDto>>) {
                val instructor: List<InstructorDto> = (response.body() ?: mutableListOf())
                instructor.forEach { pr ->
                    Log.d("WWW",pr.name.toString() + " "  + pr.experienceYears.toString() + " " + pr.rating.toString());

                    InstructorList.add(pr)
                }
                /*val adapter = InstructorAdapter(InstructorList)
                recyclerViewInstructor.adapter = adapter*/
            }
            override fun onFailure(call: Call<List<InstructorDto>>, t: Throwable) {
                Log.d("WWW", "Error:\n"+t.message)
            }
        })

    }


    fun getSchedule (){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:5246/") //адрес всегда должен заканчиваться на /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serverApi: ServerApi = retrofit.create(ServerApi::class.java)


        val request = serverApi.getSchedule() //создание, но не выполнение!
        request.enqueue(object : Callback<List<SessionDto>> {
            override fun onResponse(call: Call<List<SessionDto>>, response: Response<List<SessionDto>>) {
                val instructor: List<SessionDto>  = (response.body() ?: mutableListOf())
                instructor.forEach { pr ->
                    Log.d("WWW",pr.title.toString() + " " + pr.description.toString() + " " + pr.type.toString() + " " + pr.durationMins.toString()+ " " + pr.startDate.toString() + pr.price.toString()+ pr.instructor.toString()+ pr.bookingsCount.toString())
                    SessionList.add(pr)
                }
                // 4. Инициализация и подключение адаптера
                /*val adapter = SessionAdapter(SessionList)
                recyclerView.adapter = adapter*/
            }
            override fun onFailure(call: Call<List<SessionDto>>, t: Throwable) {
                Log.d("WWW", "Error:\n"+t.message)
            }
        })

    }
}


