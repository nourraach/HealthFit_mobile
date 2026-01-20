package com.example.projetintegration.data.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    // Configuration pour émulateur ET appareil physique
    private const val EMULATOR_BASE_URL = "http://10.0.2.2:8089/api/"
    private const val DEVICE_BASE_URL = "http://192.168.1.15:8089/api/"
    
    // Utiliser l'IP locale pour appareil physique par défaut
    private const val BASE_URL = DEVICE_BASE_URL
    private const val DEV_MODE = true

    private var retrofit: Retrofit? = null
    
    fun initialize(context: Context) {
        if (retrofit == null) {
            // Log de la configuration réseau
            android.util.Log.d("RetrofitClient", "🌐 Configuration réseau:")
            android.util.Log.d("RetrofitClient", "   BASE_URL: $BASE_URL")
            android.util.Log.d("RetrofitClient", "   Émulateur: $EMULATOR_BASE_URL")
            android.util.Log.d("RetrofitClient", "   Appareil: $DEVICE_BASE_URL")
            
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            
            val authInterceptor = AuthInterceptor(context.applicationContext)
            
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)  // Augmenté de 30s à 60s
                .readTimeout(120, TimeUnit.SECONDS)    // Augmenté de 30s à 120s (2 minutes)
                .writeTimeout(60, TimeUnit.SECONDS)    // Augmenté de 30s à 60s
                .callTimeout(180, TimeUnit.SECONDS)    // Ajouté: timeout global de 3 minutes
                .build()
            
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
    
    private fun getRetrofit(): Retrofit {
        return retrofit ?: throw IllegalStateException("RetrofitClient must be initialized first")
    }
    
    // Méthode publique pour les tests
    fun getRetrofitInstance(): Retrofit {
        return getRetrofit()
    }
    
    val authApiService: AuthApiService
        get() = getRetrofit().create(AuthApiService::class.java)
    
    val programmeApiService: ProgrammeApiService
        get() = getRetrofit().create(ProgrammeApiService::class.java)
    
    val platApiService: PlatApiService
        get() = getRetrofit().create(PlatApiService::class.java)
    
    val userApiService: UserApiService
        get() = getRetrofit().create(UserApiService::class.java)
    
    val chatBotApiService: ChatBotApiService
        get() = getRetrofit().create(ChatBotApiService::class.java)
    
    val messageApiService: MessageApiService
        get() = getRetrofit().create(MessageApiService::class.java)
    
    val favoriApiService: FavoriApiService
        get() = getRetrofit().create(FavoriApiService::class.java)
}
