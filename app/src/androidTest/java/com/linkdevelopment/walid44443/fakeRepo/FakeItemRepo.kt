package com.weightwatchers.ww_exercise_01.fakeRepo

import com.linkdevelopment.walid44443.api.base.service.ItemServiceBase
import com.linkdevelopment.walid44443.models.response.Article
import com.linkdevelopment.walid44443.models.response.NewsDataListResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeItemRepo(private val isSuccess: Boolean) : ItemServiceBase {

    override suspend fun getDate(source: String, apiKey: String): Response<NewsDataListResponse> {
        if (isSuccess)
            return Response.success(NewsDataListResponse().apply {
                this.articles = listOf(
                    Article("Walid","test desc","sss","test","testUrl","testImage"),
                    Article("Walid","test desc","sss","test","testUrl","testImage"),
                    Article("Walid","test desc","sss","test","testUrl","testImage"),
                    Article("Walid","test desc","sss","test","testUrl","testImage"),
                )
            })
        else
            return Response.error(404, "{}".toResponseBody("application/json".toMediaTypeOrNull()))
    }
}