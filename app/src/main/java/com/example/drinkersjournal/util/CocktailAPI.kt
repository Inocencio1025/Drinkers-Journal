package com.example.drinkersjournal.util

import com.example.drinkersjournal.data.DrinksByIngredients
import com.example.drinkersjournal.data.Drinks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailAPI {

    // Retrieves drink by id
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") drinkID: String): Response<Drinks>

    // Retrieves a random drink
    @GET("random.php")
    suspend fun getRandomCocktail(): Response<Drinks>


    //
    @GET("filter.php")
    suspend fun getDrinksByIngredient(@Query("i") drinkID: String): Response<DrinksByIngredients>






}