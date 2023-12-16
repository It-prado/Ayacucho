package com.example.charlaayacucho.data

import com.example.charlaayacucho.data.firebase.models.MoviesRealTime
import com.example.charlaayacucho.data.generalModelsz.ResponseData
import com.example.charlaayacucho.data.mapper.GeneralMapper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class Repository(
    private val firebaseRealTime: FirebaseDatabase,
    private val generalMapper: GeneralMapper
) {

    suspend fun fetchMovies(path: String): ResponseData<List<MoviesRealTime?>> {
        val result = firebaseRealTime.reference.child(path).get().await()
        return generalMapper.taskSnapshotToMoviesRealTime(result)
    }

    suspend fun getMovie(path: String): ResponseData<MoviesRealTime?> {
        val result = firebaseRealTime.reference.child(path).get().await()
        return generalMapper.taskSnapshotToMovieRealTime(result)
    }
}