package com.example.drinkersjournal.data


//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {


    suspend fun insertFavoriteDrink(drink: Drink)

    suspend fun deleteFavoriteDrink(drink: Drink)

    suspend fun getFavoriteDrink(id: Int): Drink?

    fun getFavoriteDrinks(): Flow<Drinks>
}