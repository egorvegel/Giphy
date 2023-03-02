package com.example.giphy


import com.example.giphy.model.TestGifEntity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search?")
    fun getGifs(
        @Query("q") query: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("bundle") bundle: String = BUNDLE
    ): Call<TestGifEntity>

    companion object {
        private var API_KEY = "PyUs0cZ10ht02e0X1z2Bm2eUxQVjcypA"
        private var BUNDLE = "messaging_non_clips"
        private var BASE_URL = "https://api.giphy.com/v1/gifs/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}