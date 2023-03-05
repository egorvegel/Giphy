//package com.example.giphy.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.giphy.model.DataGifEntity
//import com.example.giphy.repository.MainRepository
//
//class GifsPagingSource (private val repository: MainRepository) :
//    PagingSource<Int, DataGifEntity>() {
//
//    override fun getRefreshKey(state: PagingState<Int, DataGifEntity>): Int? {
//        TODO()
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataGifEntity> {
//        return try {
//            val currentPage = params.key ?: 0
//            val responce = repository.getGifs("cat", 0)
//            val data = responce.body()
//
//            LoadResult.Page(
//                data = responce.body()
//            )
//        } catch (e: Exception){
//
//        }
//    }
//}