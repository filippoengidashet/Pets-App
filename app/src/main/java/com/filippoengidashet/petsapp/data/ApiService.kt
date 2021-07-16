package com.filippoengidashet.petsapp.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * @author Filippo 15/07/2021
 */
interface ApiService {

    @Headers(Constants.ApiConfig.HEADER_KEY + ": " + Constants.ApiConfig.API_KEY)
    @GET(Constants.ENDPOINT_BREEDS)
    suspend fun getBreeds(): List<Breed>

    @Headers(Constants.ApiConfig.HEADER_KEY + ": " + Constants.ApiConfig.API_KEY)
    @GET(Constants.ENDPOINT_BREED_SEARCH)
    suspend fun searchBreed(
        @Query("breed_ids") id: String, @Query("limit") limit: Int = 8
    ): List<BreedSearch>
}
