package com.filippoengidashet.petsapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.petsapp.data.ApiService
import com.filippoengidashet.petsapp.data.BreedSearch
import com.filippoengidashet.petsapp.data.Constants
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

/**
 * @author Filippo 15/07/2021
 */
class BreedSearchViewModel : ViewModel() {

    private val apiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _breedSearch = MutableLiveData<List<BreedSearch>>()
    fun getBreedSearchLiveData(): LiveData<List<BreedSearch>> = _breedSearch

    fun searchBreed(breedId: String, callback: (msg: String?) -> Unit) {
        viewModelScope.launch {
            try {
                _breedSearch.value = apiService.searchBreed(breedId)
            } catch (e: Exception) {
                callback(e.message)
            }
        }
    }
}
