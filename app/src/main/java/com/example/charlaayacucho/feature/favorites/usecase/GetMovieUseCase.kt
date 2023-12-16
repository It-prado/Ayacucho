package com.example.charlaayacucho.feature.favorites.usecase

import com.example.charlaayacucho.data.Repository
import com.example.charlaayacucho.data.generalModelsz.ResponseData
import com.example.charlaayacucho.data.mapper.GeneralMapper
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.google.firebase.FirebaseException
import kotlinx.coroutines.delay



sealed class GetMovieUseCaseResult {
    data class Success(val result: MoviesDomain?) : GetMovieUseCaseResult()
    data class Error(val result: String) : GetMovieUseCaseResult()
}

class GetMovieUseCase(
    private val repository: Repository,
    private val generalMapper: GeneralMapper
) {

    suspend fun invoke(id: String): GetMovieUseCaseResult {
        return try {
            delay(1000)
            when (val item = repository.fetchMovies("movies")) {
                is ResponseData.Error -> {
                    throw FirebaseException(item.result.message.orEmpty())
                }

                is ResponseData.Success -> {
                    val notifications = generalMapper.moviesRealTimeToDomain(item.result)
                    GetMovieUseCaseResult.Success(notifications.firstOrNull { it.id == id })
                }
            }
        } catch (e: FirebaseException) {
            GetMovieUseCaseResult.Error(e.message.toString())
        }
    }
}