package com.linkdevelopment.walid44443.repository

import com.linkdevelopment.walid44443.api.ClientAPI
import com.linkdevelopment.walid44443.api.base.BaseClientApi
import com.linkdevelopment.walid44443.conf.SharedCodeConfiguration
import com.linkdevelopment.walid44443.models.response.NewsDataListResponse
import com.linkdevelopment.walid44443.repository.base.ItemRepoBase
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.StreamResetException

class ArticlesRepo(clientAPI: BaseClientApi = ClientAPI()) : ItemRepoBase(client = clientAPI) {

    override suspend fun getItemList(): NewsDataListResponse {
        var responseBase = NewsDataListResponse()
        try {
            this@ArticlesRepo.client.itemService?.getDate(
                apiKey = SharedCodeConfiguration.apiKey,
                source = SharedCodeConfiguration.targetSource
            )?.also {
                if (it.isSuccessful)
                    it.body()?.let { response ->
                        responseBase = response
                    }
                else
                    responseBase.error = it.errorBody().toString()

            }
        } catch (e: Throwable) {
            if (e is StreamResetException) {
                if (e.errorCode == ErrorCode.CANCEL) {
                    getItemList()
                } else {
                    responseBase.apply {
                        error = e.message
                    }
                }
            } else {
                responseBase.apply {
                    error = e.message
                }
            }
        } catch (e: Exception) {
            responseBase.apply {
                error = e.message
            }
        }
        return responseBase
    }
}