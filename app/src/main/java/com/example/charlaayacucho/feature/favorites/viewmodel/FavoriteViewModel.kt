package com.example.charlaayacucho.feature.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.charlaayacucho.data.Repository
import com.example.charlaayacucho.data.mapper.GeneralMapper
import com.example.charlaayacucho.feature.favorites.usecase.GetMovieUseCase
import com.example.charlaayacucho.feature.favorites.usecase.GetMovieUseCaseResult
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.example.charlaayacucho.feature.homeScreen.usecase.GetMoviesUseCase
import com.example.charlaayacucho.feature.homeScreen.usecase.GetMoviesUseCaseResult
import com.example.charlaayacucho.feature.homeScreen.viewmodel.HomeViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val useCase: GetMovieUseCase,
    private val useCaseTwo: GetMoviesUseCase
) : ViewModel() {

    private val _movie: MutableLiveData<MoviesDomain?> = MutableLiveData()
    val movie: LiveData<MoviesDomain?> = _movie

    private val _movies: MutableLiveData<List<MoviesDomain>?> = MutableLiveData()
    val movies: LiveData<List<MoviesDomain>?> = _movies

    private val _isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val isLoading: LiveData<Boolean?> = _isLoading

    init {
        fetchMovies()
    }

    fun getMovieDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        when (val result = useCase.invoke(id = id)) {
            is GetMovieUseCaseResult.Error -> {
                _isLoading.postValue(false)
                _movie.postValue(null)
            }

            is GetMovieUseCaseResult.Success -> {
                _isLoading.postValue(false)
                _movie.postValue(result.result)
            }
        }
    }

    fun fetchMovies() = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        delay(1000)
        when (val result = useCaseTwo.invoke()) {
            is GetMoviesUseCaseResult.Error -> {
                _isLoading.postValue(false)
                _movies.postValue(null)
            }

            is GetMoviesUseCaseResult.Success -> {
                _isLoading.postValue(false)
                _movies.postValue(result.result)
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
                    GetMovieUseCase(repository = repository, generalMapper = generalMapper)
                val useCaseTwo =
                    GetMoviesUseCase(repository = repository, generalMapper = generalMapper)
                FavoriteViewModel(useCase, useCaseTwo)
            }
        }
    }
}