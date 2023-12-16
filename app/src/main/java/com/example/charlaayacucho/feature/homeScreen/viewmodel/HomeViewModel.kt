package com.example.charlaayacucho.feature.homeScreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.charlaayacucho.data.Repository
import com.example.charlaayacucho.data.mapper.GeneralMapper
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.example.charlaayacucho.feature.homeScreen.usecase.GetMoviesUseCase
import com.example.charlaayacucho.feature.homeScreen.usecase.GetMoviesUseCaseResult
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetMoviesUseCase) : ViewModel() {

    private val _list: MutableLiveData<List<MoviesDomain>> = MutableLiveData()
    val list: LiveData<List<MoviesDomain>> = _list

    init {
        fetchMovies()
    }

    private fun fetchMovies() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = useCase.invoke()) {
            is GetMoviesUseCaseResult.Error -> {
                _list.postValue(emptyList())
            }

            is GetMoviesUseCaseResult.Success -> {
                _list.postValue(result.result)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val firebase = Firebase.database
                val generalMapper = GeneralMapper()
                val repository =
                    Repository(firebaseRealTime = firebase, generalMapper = generalMapper)
                val useCase =
                    GetMoviesUseCase(repository = repository, generalMapper = generalMapper)
                HomeViewModel(useCase)
            }
        }
    }
}