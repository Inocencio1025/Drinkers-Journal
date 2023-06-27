package com.example.drinkersjournal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.drinkersjournal.util.FavDrinkSerializer
import com.example.drinkersjournal.util.FavDrinksDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences



class MainActivity : ComponentActivity() {
    private val Context.myFavDrinksDataStore: DataStore<ListOfDrinks> by dataStore(
        fileName = "favList.pb",
        serializer = FavDrinkSerializer,
    )
    private val favDrinksDataStore: FavDrinksDataStore
        get() = FavDrinksDataStore.getInstance(myFavDrinksDataStore)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            LaunchedEffect(Unit) {

                DrinkersInfo.setDataStore(favDrinksDataStore)
                /*
                favDrinksDataStore.saveNewDrink("17060")
                favDrinksDataStore.saveNewDrink("17020")
                favDrinksDataStore.saveNewDrink("13395")
                favDrinksDataStore.saveNewDrink("14688")

                 */


                DrinkersInfo.setList(favDrinksDataStore.favDrinksFlow)
            }




            Navigation()

            /*
            //test drinks for favorites list
            DrinkersInfo.addTestDrink("17060", 0, "asss checkss")
            DrinkersInfo.addTestDrink("17020", 0, "")
            DrinkersInfo.addTestDrink("13395", 1, "woblles")
            DrinkersInfo.addTestDrink("14688", 2, "")
            DrinkersInfo.addTestDrink("12762", 9, "kkjhgh")

             */


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
