package com.linkdevelopment.walid44443.api.base

import com.linkdevelopment.walid44443.api.base.service.ItemServiceBase

abstract class BaseClientApi {
    open val itemService: ItemServiceBase? = null
}