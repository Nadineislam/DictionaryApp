package com.example.dictionaryapp.feature_dictionary

import com.example.dictionaryapp.core.util.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WordInfoUseCaseTest {
    private lateinit var successWordInfoUseCase: FakeWordInfoUseCase
    private lateinit var errorWordInfoUseCase: FakeWordInfoUseCase


    @Before
    fun setup() {
        successWordInfoUseCase = FakeWordInfoUseCase(fakeWordInfoRepository)
        errorWordInfoUseCase = FakeWordInfoUseCase(fakeErrorWordInfoRepository)

    }

    @Test
    fun `when word is blank then use case should return empty flow`() = runBlocking {

        val result = successWordInfoUseCase("").toList()

        assertTrue((result[0] as Resource.Success).data.isNullOrEmpty())
    }

    @Test
    fun `when word is not blank and repository returns success then use case should return success flow`() =
        runBlocking {

            val result = successWordInfoUseCase("example").toList()

            assertEquals(expectedWordInfo, (result[0] as Resource.Success).data)
        }

    @Test
    fun `when word is not blank and repository returns error then use case should return error flow`() =
        runBlocking {

            val result = errorWordInfoUseCase("example").toList()

            assertEquals("Failed to get word info", (result[0] as Resource.Error).message)
        }

}