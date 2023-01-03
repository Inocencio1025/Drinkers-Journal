package com.example.drinkersjournal

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CocktailAPI {

    // Retrieves a random drink
    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomCocktail(): Response<Drinks>

    @GET("/api/json/v1/1/lookup.php?i={drinkID}")
    suspend fun getCocktailById(@Path("drinkID") drinkID: Int): Response<Drinks>



}