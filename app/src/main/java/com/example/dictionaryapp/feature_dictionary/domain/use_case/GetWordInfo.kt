package com.example.dictionaryapp.feature_dictionary.domain.use_case

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWordInfo @Inject constructor(private val repository: WordInfoRepository) {
    operator fun invoke(word:String):Flow<Resource<List<WordInfo>>>{
        if(word.isBlank()){
            return flow {  }
        }
        return repository.getWordInfo(word)
    }
}