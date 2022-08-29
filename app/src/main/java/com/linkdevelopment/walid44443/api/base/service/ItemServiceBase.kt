package com.linkdevelopment.walid44443.api.base.service

import com.linkdevelopment.walid44443.models.response.NewsDataListResponse
import retrofit2.Response

interface ItemServiceBase {
    suspend fun getDate(
        source: String,
        apiKey: String
    ): Response<NewsDataListResponse>
}