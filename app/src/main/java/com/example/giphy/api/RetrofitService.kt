package com.example.giphy.api


import com.example.giphy.model.DataGifEntity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search?")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("api_key") api_key: String = API_KEY,
        @Query("bundle") bundle: String = BUNDLE,
        @Query("limit") limit: Int = 25
    ): Response<DataGifEntity>

    companion object {
        private var API_KEY = "PyUs0cZ10ht02e0X1z2Bm2eUxQVjcypA"
        private var BUNDLE = "messaging_non_clips"
        private var BASE_URL = "https://api.giphy.com/v1/gifs/"
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}