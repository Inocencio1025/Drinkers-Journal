package com.example.drinkersjournal.util

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.drinkersjournal.util.FavDrinkSerializer
import com.example.drinkersjournal.ListOfDrinks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import okio.IOException


class FavDrinksDataStore private constructor(private val FavDrinksStore: DataStore<ListOfDrinks>) {

    private val TAG: String = "FavDrinksDataStore"

    val favDrinksFlow: Flow<ListOfDrinks> = FavDrinksStore.data.catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            Log.e(TAG, "Error reading sort order preferences.", exception)
            emit(ListOfDrinks.getDefaultInstance())
        } else {
            throw exception
        }
    }

    suspend fun saveNewDrink(newDrink: String) {
        FavDrinksStore.updateData { drink ->
            if (drink.toBuilder().drinkIDList.contains(newDrink)) {
                drink
            } else {
                drink.toBuilder().addDrinkID(newDrink).build()
            }
        }
    }

    suspend fun clearListOfDrinks() {
        FavDrinksStore.updateData { list ->
            list.toBuilder().clear().build()
        }
    }

    suspend fun removeDrink(drink: String) {
        FavDrinksStore.updateData { drinks ->
            val existingDrink = drinks.toBuilder().drinkIDList
            if (existingDrink.contains(drink)) {
                val newList = existingDrink.filter { it != drink }
                drinks.toBuilder().clear().addAllDrinkID(newList).build()
            } else {
                drinks
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FavDrinksDataStore? = null

        fun getInstance(dataStore: DataStore<ListOfDrinks>): FavDrinksDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = FavDrinksDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
