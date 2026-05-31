package com.vasilisa.iskraclientapp.data.api

import android.content.Context
import com.vasilisa.iskraclientapp.data.storage.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "http://localhost:5246/"

    fun create(context: Context): ServerApi {

        val tokenManager =
            TokenManager(context)

        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    JwtInterceptor {

                        tokenManager.getToken()
                    }
                )
                .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ServerApi::class.java)
    }
}