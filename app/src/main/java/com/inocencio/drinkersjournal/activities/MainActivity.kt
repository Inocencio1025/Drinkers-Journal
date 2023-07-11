package com.inocencio.drinkersjournal.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.inocencio.drinkersjournal.datastore.FavDrinkSerializer
import com.inocencio.drinkersjournal.datastore.FavDrinksDataStore
import com.inocencio.drinkersjournal.screens.setBrowseList
import com.inocencio.drinkersjournal.ui.theme.DrinkersJournalTheme
import com.inocencio.drinkersjournal.util.DrinkersInfo
import com.inocencio.drinkersjournal.util.Navigation


class MainActivity : ComponentActivity() {

    // Instantiate datastore (for loading/saving at app start)
    private val Context.myFavDrinksDataStore: DataStore<com.inocencio.drinkersjournal.ListOfDrinks> by dataStore(
        fileName = "favList.pb",
        serializer = FavDrinkSerializer,
    )
    private val favDrinksDataStore: FavDrinksDataStore
        get() = FavDrinksDataStore.getInstance(myFavDrinksDataStore)


    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.Black.hashCode()
        super.onCreate(savedInstanceState)
        setContent {
            DrinkersJournalTheme {
                // loads in favorites list
                setBrowseList()
                LaunchedEffect(Unit) {
                    DrinkersInfo.setDataStore(favDrinksDataStore)
                    DrinkersInfo.setList(favDrinksDataStore.favDrinksFlow)
                }// end load

                Navigation()// Navigates user to HomeScreen (Start of app)
            }
        }
    }
}
