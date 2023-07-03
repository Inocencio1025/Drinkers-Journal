package com.example.drinkersjournal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.drinkersjournal.screens.setBrowseList
import com.example.drinkersjournal.ui.theme.DrinkersJournalTheme
import com.example.drinkersjournal.util.FavDrinkSerializer
import com.example.drinkersjournal.util.FavDrinksDataStore
import com.example.drinkersjournal.util.Navigation


class MainActivity : ComponentActivity() {
    private val Context.myFavDrinksDataStore: DataStore<ListOfDrinks> by dataStore(
        fileName = "favList.pb",
        serializer = FavDrinkSerializer,
    )
    private val favDrinksDataStore: FavDrinksDataStore
        get() = FavDrinksDataStore.getInstance(myFavDrinksDataStore)


    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.Black.hashCode()

        super.onCreate(savedInstanceState)
        //setTheme(R.style.Theme_DrinkersJournal)
        setContent {
            DrinkersJournalTheme{

                setBrowseList()
                LaunchedEffect(Unit) {
                    DrinkersInfo.setDataStore(favDrinksDataStore)
                    DrinkersInfo.setList(favDrinksDataStore.favDrinksFlow)
                }
                Navigation()
            }
        }
    }
}
