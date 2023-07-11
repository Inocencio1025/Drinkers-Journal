package com.inocencio.drinkersjournal.datastore

import androidx.datastore.core.DataStore
import com.inocencio.drinkersjournal.ListOfDrinks
import com.inocencio.drinkersjournal.ProtoDrink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import okio.IOException

class FavDrinksDataStore private constructor(private val FavDrinksStore: DataStore<ListOfDrinks>) {

    val favDrinksFlow: Flow<ListOfDrinks> =
        FavDrinksStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(ListOfDrinks.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun saveNewDrink(newProtoDrink: ProtoDrink.Builder) {

        FavDrinksStore.updateData { drinks ->
            val existingList = drinks.toBuilder().protoDrinkList

            if (existingList.any { it.id == newProtoDrink.id }) {
                drinks
            } else {
                drinks.toBuilder()
                    .addProtoDrink(newProtoDrink)
                    .build()
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
            val existingList = drinks.toBuilder().protoDrinkList
            if (existingList.any { it.id == drink }) {
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

