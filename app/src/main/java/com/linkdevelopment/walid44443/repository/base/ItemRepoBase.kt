package com.linkdevelopment.walid44443.repository.base

import com.linkdevelopment.walid44443.api.ClientAPI
import com.linkdevelopment.walid44443.api.base.BaseClientApi
import com.linkdevelopment.walid44443.models.response.NewsDataListResponse

abstract class ItemRepoBase(
    val client: BaseClientApi = ClientAPI()
) {
    open suspend fun getItemList(): NewsDataListResponse {
        return NewsDataListResponse()
    }
}