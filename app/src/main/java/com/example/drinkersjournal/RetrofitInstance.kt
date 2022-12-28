package com.example.drinkersjournal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    // lazy means instantiated when accessing api
    val api: CocktailAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailAPI::class.java);
    }
}