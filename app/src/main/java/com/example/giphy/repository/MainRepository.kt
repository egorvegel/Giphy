package com.example.giphy.repository

import com.example.giphy.api.RetrofitService
import com.example.giphy.model.DataGifEntity
import retrofit2.Response

class MainRepository(
    private val apiInterface: RetrofitService,
) {
    suspend fun getGifs(query: String, offset: Int): Response<DataGifEntity> {
        return apiInterface.getGifs(query, offset)
    }
}