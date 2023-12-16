package com.example.charlaayacucho.feature.homeScreen.usecase

import com.example.charlaayacucho.data.Repository
import com.example.charlaayacucho.data.generalModelsz.ResponseData
import com.example.charlaayacucho.data.mapper.GeneralMapper
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.google.firebase.FirebaseException

sealed class GetMoviesUseCaseResult {
    data class Success(val result: List<MoviesDomain>) : GetMoviesUseCaseResult()
    data class Error(val result: String) : GetMoviesUseCaseResult()
}

class GetMoviesUseCase(
    private val repository: Repository,
    private val generalMapper: GeneralMapper
) {

    suspend fun invoke(): GetMoviesUseCaseResult {
        return try {
            when (val item = repository.fetchMovies("movies")) {
                is ResponseData.Error -> {
                    throw FirebaseException(item.result.message.orEmpty())
                }

                is ResponseData.Success -> {
                    val notifications = generalMapper.moviesRealTimeToDomain(item.result)
                    GetMoviesUseCaseResult.Success(notifications)
                }
            }
        } catch (e: FirebaseException) {
            GetMoviesUseCaseResult.Error(e.message.toString())
        }
    }

}