package cat.deim.asm40.pedalean2.data.datasource.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.pedalean2.com/"
    const val SERVER_TOKEN = "server_token_lasm_2025"

    private var tokenStorage: TokenStorage? = null

    /** Llamar una vez al arrancar la app (Application/Splash) con el applicationContext. */
    fun init(storage: TokenStorage) { tokenStorage = storage }

    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .followRedirects(false)
            .followSslRedirects(false)

        // Solo añadimos el authenticator si ya tenemos storage
        tokenStorage?.let { builder.authenticator(TokenAuthenticator(it, SERVER_TOKEN)) }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}