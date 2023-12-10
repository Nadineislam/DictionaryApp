package com.example.dictionaryapp.feature_dictionary.domain.use_case

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface IWordInfoUseCase {
    operator fun invoke(word:String): Flow<Resource<List<WordInfo>>>
}