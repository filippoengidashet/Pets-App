package com.filippoengidashet.petsapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.petsapp.data.ApiService
import com.filippoengidashet.petsapp.data.Breed
import com.filippoengidashet.petsapp.data.Constants
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Filippo 15/07/2021
 */
class BreedsViewModel : ViewModel() {

    private val apiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _breeds = MutableLiveData<List<Breed>>()
    fun getBreedsLiveData(): LiveData<List<Breed>> = _breeds

    fun loadBreeds(
        progressCallback: (state: ProgressState) -> Unit = {},
        errorCallback: (msg: String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                progressCallback(ProgressState.Started)
                _breeds.value = apiService.getBreeds()
            } catch (e: Exception) {
                errorCallback(e.message)
            } finally {
                progressCallback(ProgressState.Completed)
            }
        }
    }

    sealed class ProgressState {
        object Started : ProgressState()
        object Completed : ProgressState()
    }
}
