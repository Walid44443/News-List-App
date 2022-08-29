package com.linkdevelopment.walid44443.models.response

data class NewsDataListResponse(
    var articles: List<Article>? = emptyList(),
    var sortBy: String? = null,
    var source: String? = null,
    var status: String? = null,
    var error: String? = null
)