package com.example.storezaapdemo


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    private val BASE_URL = "https://storezaap.com/mpp/Android_Test_API/"
    private val builder = OkHttpClient.Builder()
    private val interceptor = HttpLoggingInterceptor()

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private var retrofitClient: RetrofitClient? = null
        private lateinit var retrofit: Retrofit

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (retrofitClient == null) {
                retrofitClient = RetrofitClient()
            }
            return retrofitClient as RetrofitClient
        }
    }

    fun getApi(): Api {
        return retrofit.create(Api::class.java)
    }
}