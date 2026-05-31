import com.vasilisa.hello.data.api.JwtInterceptor
import com.vasilisa.hello.data.api.ServerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.34.183.85:5246/"

    var jwtToken: String? = null

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            JwtInterceptor {
                jwtToken
            }
        )
        .build()

    val api: ServerApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServerApi::class.java)
    }
}