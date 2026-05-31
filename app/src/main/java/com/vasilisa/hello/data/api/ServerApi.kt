package com.vasilisa.hello.data.api

import com.vasilisa.hello.data.dto.InstructorDto
import com.vasilisa.hello.data.dto.LoginRequestDto
import com.vasilisa.hello.data.dto.LoginResponseDto
import com.vasilisa.hello.data.dto.RegisterRequestDto
import com.vasilisa.hello.data.dto.SessionDto
import com.vasilisa.hello.data.dto.UserProfileDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {

    @POST("auth/register")
    fun register(
        @Body dto: RegisterRequestDto
    ): Call<String>

    @POST("auth/login")
    fun login(
        @Body dto: LoginRequestDto
    ): Call<LoginResponseDto>

    @GET("user/profile")
    fun getProfile(): Call<UserProfileDto>

    @GET("user/bookings")
    fun getUserBookings(): Call<List<SessionDto>>

    @GET("instructors")
    fun getInstructors(): Call<List<InstructorDto>>

    @GET("schedule")
    fun getSchedule(): Call<List<SessionDto>>

    @POST("user/book/{sessionId}")
    fun bookSession(
        @Path("sessionId") sessionId: String
    ): Call<Void>

    @DELETE("user/book/{sessionId}")
    fun cancelBooking(
        @Path("sessionId") sessionId: String
    ): Call<Void>
}