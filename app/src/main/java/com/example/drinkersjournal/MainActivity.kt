package com.example.drinkersjournal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.drinkersjournal.screens.setBrowseScreen
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
        setContent {
            DrinkersJournalTheme{

                setBrowseScreen()
                LaunchedEffect(Unit) {
                    DrinkersInfo.setDataStore(favDrinksDataStore)
                    DrinkersInfo.setList(favDrinksDataStore.favDrinksFlow)
                }
                Navigation()
            }


        }
    }

    /*
    suspend fun setFavList(favDrinkList: FavDrinkList) {
        dataStore.updateData {
            it.copy(
                favDrinkList = favDrinkList.favDrinkList
            )
        }
    }



    override fun onResume() {
        super.onResume()
        // Fetching the stored data from the SharedPreference
        val sh = getSharedPreferences("drinkList", MODE_PRIVATE)
        var isDone = false
        var drinkNum = 1
        while(!isDone){
            val a = sh.getInt(drinkNum.toString(), 0)
            DrinkersInfo.storedDrinkIDs.add(a)

            if()
        }


        // Setting the fetched data in the EditTexts

    }

    //called when app closes
    override fun onPause() {
        super.onPause()
        var drinkNum = 1

        // Creating a shared pref object with a file name "drinkList" in private mode
        val sharedPreferences = getSharedPreferences("drinkList", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        // write all the data entered by the user in SharedPreference and apply
        DrinkersInfo.favDrinksList.forEach{
            myEdit.putInt(drinkNum.toString(), it.idDrink.toInt())
            drinkNum++;
        }
        myEdit.apply()
    }



     */
}
