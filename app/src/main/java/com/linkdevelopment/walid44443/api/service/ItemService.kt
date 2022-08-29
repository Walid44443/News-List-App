package com.linkdevelopment.walid44443.api.service

import com.linkdevelopment.walid44443.api.base.service.ItemServiceBase
import com.linkdevelopment.walid44443.models.response.NewsDataListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService : ItemServiceBase {
    @GET("/v1/articles")
    override suspend fun getDate(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsDataListResponse>
}