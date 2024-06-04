package com.jimenez.app.data.network.endpoints

import com.jimenez.app.data.network.entities.allNews.AllNews
import com.jimenez.app.data.network.entities.topNews.NewsApi
import retrofit2.Response
import retrofit2.http.GET

interface NewsEndpoint {

    @GET("top")
    suspend fun getAllTopNews(): Response<NewsApi>

    @GET("all")
    suspend fun getAllNews(): Response<AllNews>

}