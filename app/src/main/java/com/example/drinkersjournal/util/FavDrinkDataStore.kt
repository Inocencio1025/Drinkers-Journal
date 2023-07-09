package com.example.drinkersjournal.util

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.drinkersjournal.ListOfDrinks
import com.example.drinkersjournal.ProtoDrink
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

    suspend fun saveNewDrink(newProtoDrink: ProtoDrink.Builder) {

        FavDrinksStore.updateData { drinks ->
            val existingList = drinks.toBuilder().protoDrinkList

            if (existingList.any{ it.id == newProtoDrink.id}){
                drinks
            } else {
                drinks.toBuilder()
                    .addProtoDrink(newProtoDrink)
                    .build()
            }

        }
        Log.e("LOOK", newProtoDrink.id + " -------- " + newProtoDrink.ratingNum)

    }

    suspend fun clearListOfDrinks() {
        FavDrinksStore.updateData { list ->
            list.toBuilder().clear().build()
        }
    }

    suspend fun removeDrink(drink: String) {
        FavDrinksStore.updateData { drinks ->
            val existingList = drinks.toBuilder().protoDrinkList
            if (existingList.any{ it.id == drink}) {
                val newList = existingList.filter { it.id != drink }
                drinks.toBuilder().clear().addAllProtoDrink(newList).build()
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
