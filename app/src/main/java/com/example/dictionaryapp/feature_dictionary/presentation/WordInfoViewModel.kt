package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.use_case.WordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(private val getWordInfo: WordInfoUseCase) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _wordInfoState = MutableStateFlow(WordInfoState())
    val wordInfoState = _wordInfoState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            _wordInfoState.value = wordInfoState.value.copy(
                isLoading = true
            )
            getWordInfo(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _wordInfoState.value = wordInfoState.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _wordInfoState.value = wordInfoState.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(result.message?:"Unknown error"))
                        }
                        is Resource.Loading -> {
                            _wordInfoState.value = wordInfoState.value.copy(
                                wordInfoItems = result.data?:emptyList(),
                                isLoading = true
                            )
                        }
                    }

                }.launchIn(this)
        }

    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}