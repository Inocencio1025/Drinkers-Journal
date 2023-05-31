package com.example.drinkersjournal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDrink(drink: Drink)

    @Delete
    suspend fun deleteFavoriteDrink(drink: Drink)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getFavoriteDrink(id: Int): Drink?

    @Query("SELECT * FROM drink")
    fun getFavoriteDrinks(): Flow<Drinks>
}