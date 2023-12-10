package com.example.dictionaryapp.feature_dictionary

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dictionary.domain.use_case.IWordInfoUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWordInfoUseCase (private val wordInfoRepository: WordInfoRepository) : IWordInfoUseCase {
    override operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {

        if (word.isBlank()) {
            return flowOf(Resource.Success(emptyList()))
        }
        return wordInfoRepository.getWordInfo(word)
    }
}

