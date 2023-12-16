package com.example.charlaayacucho.data.mapper

import com.example.charlaayacucho.data.firebase.models.MoviesRealTime
import com.example.charlaayacucho.data.generalModelsz.ResponseData
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseException

class GeneralMapper {
    fun taskSnapshotToMoviesRealTime(result: DataSnapshot): ResponseData<List<MoviesRealTime?>> {
        return try {
            ResponseData.Success(result.children.map {
                it.getValue(MoviesRealTime::class.java)
            })
        } catch (e: DatabaseException) {
            ResponseData.Error(e)
        }
    }

    fun moviesRealTimeToDomain(result: List<MoviesRealTime?>): List<MoviesDomain> {
        return result.map {
            MoviesDomain(
                id = it?.id.orEmpty(),
                title = it?.title.orEmpty(),
                image = it?.image.orEmpty()
            )
        }
    }

    fun taskSnapshotToMovieRealTime(result: DataSnapshot): ResponseData<MoviesRealTime?> {
        return try {
            ResponseData.Success(result.getValue(MoviesRealTime::class.java))
        } catch (e: DatabaseException) {
            ResponseData.Error(e)
        }
    }

    fun movieRealTimeToDomain(result: MoviesRealTime?): MoviesDomain {
        return MoviesDomain(
            id = result?.id.orEmpty(),
            title = result?.title.orEmpty(),
            image = result?.image.orEmpty()
        )
    }


}