package com.weightwatchers.ww_exercise_01

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weightwatchers.ww_exercise_01.fakeRepo.FakeItemRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemRepoTest {


    @Test
    @Throws(Exception::class)
    fun testItemRepoReturnSuccess() = runTest {
        val itemsRepo = FakeItemRepo(true)
        withContext(Dispatchers.Default) {
            itemsRepo.getDate("","")
        }.also {
            assertEquals(it.isSuccessful, true)
        }
    }


    @Test
    @Throws(Exception::class)
    fun testItemRepoReturnRightItemsSizeEqualsFourTrue() = runTest {
        val itemsRepo = FakeItemRepo(true)
        withContext(Dispatchers.Default) {
            itemsRepo.getDate("","")
        }.also {
            assertEquals(it.body()?.articles?.size, 4)
        }
    }


    @Test
    @Throws(Exception::class)
    fun testItemRepoReturnFailFalse() = runTest {
        val itemsRepo = FakeItemRepo(false)
        withContext(Dispatchers.Default) {
            itemsRepo.getDate("","")
        }.also {
            assertEquals(it.isSuccessful, false)
        }
    }
}
