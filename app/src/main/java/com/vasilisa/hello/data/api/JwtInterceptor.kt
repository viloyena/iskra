package com.vasilisa.hello.data.api

import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = tokenProvider()

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(request)
    }
}