package com.weightwatchers.ww_exercise_01.FakeApi

import com.linkdevelopment.walid44443.api.base.BaseClientApi
import com.weightwatchers.ww_exercise_01.fakeRepo.FakeItemRepo

class FakeClientAPI(val isSuccess: Boolean = false) : BaseClientApi() {
    override val itemService: FakeItemRepo
        get() = FakeItemRepo(isSuccess)
}