package com.example.charlaayacucho.data.generalModelsz

sealed class ResponseData<out T> {
    data class Success<T>(val result: T) : ResponseData<T>()
    data class Error(val result: Exception) : ResponseData<Nothing>()
}
