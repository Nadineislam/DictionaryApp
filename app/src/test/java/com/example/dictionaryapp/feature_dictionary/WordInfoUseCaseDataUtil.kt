package com.example.dictionaryapp.feature_dictionary

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.Definition
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val expectedWordInfo = listOf(
    WordInfo(
        meanings = listOf(
            Meaning(
                definitions = listOf(
                    Definition(
                        antonyms = listOf("antonym"),
                        definition = "definition",
                        example = "example",
                        synonyms = listOf("synonym")
                    )
                ),
                partOfSpeech = "noun"
            )
        ),
        origin = "origin",
        phonetic = "phonetic",
        word = "example"
    )
)
val fakeWordInfoRepository = object : WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Success(expectedWordInfo))
    }

}
val fakeErrorWordInfoRepository = object : WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Error("Failed to get word info"))
    }
}