package com.example.drinkersjournal

import retrofit2.Response
import retrofit2.http.GET

public interface CocktailApi {

    // all functions to use api





    // Retrieves a random drink
    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomCocktail(): Response<Drink>



}