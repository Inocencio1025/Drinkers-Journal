package com.example.drinkersjournal


import android.content.Context
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.drinkersjournal.data.Drink
import com.example.drinkersjournal.data.DrinkByIngredients
import com.example.drinkersjournal.data.Drinks
import com.example.drinkersjournal.util.FavDrinkSerializer
import com.example.drinkersjournal.util.FavDrinksDataStore
import com.example.drinkersjournal.util.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response







object DrinkersInfo {












    val favDrinksList = mutableListOf<Drink>()
    val drinksByIngredient = mutableListOf<DrinkByIngredients>()

    var imageUrlStr = mutableStateOf("")
    var drinkId = mutableStateOf("")
    var drinkName = mutableStateOf("")
    var rating = mutableStateOf("")
    var ratingText = mutableStateOf("")
    var instructions = mutableStateOf("")
    var ingredients: MutableList<String> by mutableStateOf(mutableListOf())
    var measurements: MutableList<String> by mutableStateOf(mutableListOf())


    var currentlyViewedDrinkId = ""
    var ingredientForDrinkList = ""

    var storedDrinkIDs : Flow<ListOfDrinks> = flow{}


/*
    val exampleCounterFlow: Flow<Int> = context.favListDataStore.data
        .map {  ->
            // The exampleCounter property is generated from the proto schema.
            settings.exampleCounter
        }
var favDrinksDataStore: FavDrinksDataStore
    fun setDataStore(favDrinksDataStore: FavDrinksDataStore){
        this.favDrinksDataStore = favDrinksDataStore
    }
 */





    fun retrieveDrinksByIngredient(ingredient: String){

        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getDrinksByIngredient(ingredient)
            } catch (e: IOException) {
                //Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                //Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {

                drinksByIngredient.clear()

                response.body()!!.drinks.forEach(){
                    val drinkByIngredients = DrinkByIngredients(
                        it.idDrink,
                        it.strDrink,
                        it.strDrinkThumb
                    )

                    drinksByIngredient.add(drinkByIngredients)
                }


            }
        }
    }
    // adds drink to user list
    suspend fun addDrinkById(id: String){
        if (!isInList(id)) {
            CoroutineScope(Dispatchers.Main).launch {

                val response = try {
                    RetrofitInstance.api.getCocktailById(drinkID = id)
                } catch (e: IOException) {
                    //Log.e(TAG, "IOException, you might not have internet connection")
                    return@launch
                } catch (e: HttpException) {
                    //Log.e(TAG, "HttpException, unexpected response")
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {

                    //collect all info
                    val drink = Drink(
                        response.body()!!.drinks[0].idDrink,
                        response.body()!!.drinks[0].strDrink,
                        response.body()!!.drinks[0].strDrinkAlternate,
                        response.body()!!.drinks[0].strTags,
                        response.body()!!.drinks[0].strVideo,
                        response.body()!!.drinks[0].strCategory,
                        response.body()!!.drinks[0].strIBA,
                        response.body()!!.drinks[0].strAlcoholic,
                        response.body()!!.drinks[0].strGlass,
                        response.body()!!.drinks[0].strInstructions,
                        response.body()!!.drinks[0].strInstructionsES,
                        response.body()!!.drinks[0].strInstructionsDE,
                        response.body()!!.drinks[0].strInstructionsFR,
                        response.body()!!.drinks[0].strInstructionsIT,
                        response.body()!!.drinks[0].strInstructionsZH_HANS,
                        response.body()!!.drinks[0].strInstructionsZH_HANT,
                        response.body()!!.drinks[0].strDrinkThumb,
                        response.body()!!.drinks[0].strIngredient1,
                        response.body()!!.drinks[0].strIngredient2,
                        response.body()!!.drinks[0].strIngredient3,
                        response.body()!!.drinks[0].strIngredient4,
                        response.body()!!.drinks[0].strIngredient5,
                        response.body()!!.drinks[0].strIngredient6,
                        response.body()!!.drinks[0].strIngredient7,
                        response.body()!!.drinks[0].strIngredient8,
                        response.body()!!.drinks[0].strIngredient9,
                        response.body()!!.drinks[0].strIngredient10,
                        response.body()!!.drinks[0].strIngredient11,
                        response.body()!!.drinks[0].strIngredient12,
                        response.body()!!.drinks[0].strIngredient13,
                        response.body()!!.drinks[0].strIngredient14,
                        response.body()!!.drinks[0].strIngredient15,
                        response.body()!!.drinks[0].strMeasure1,
                        response.body()!!.drinks[0].strMeasure2,
                        response.body()!!.drinks[0].strMeasure3,
                        response.body()!!.drinks[0].strMeasure4,
                        response.body()!!.drinks[0].strMeasure5,
                        response.body()!!.drinks[0].strMeasure6,
                        response.body()!!.drinks[0].strMeasure7,
                        response.body()!!.drinks[0].strMeasure8,
                        response.body()!!.drinks[0].strMeasure9,
                        response.body()!!.drinks[0].strMeasure10,
                        response.body()!!.drinks[0].strMeasure11,
                        response.body()!!.drinks[0].strMeasure12,
                        response.body()!!.drinks[0].strMeasure13,
                        response.body()!!.drinks[0].strMeasure14,
                        response.body()!!.drinks[0].strMeasure15,
                        response.body()!!.drinks[0].strImageSource,
                        response.body()!!.drinks[0].strImageAttribution,
                        response.body()!!.drinks[0].strCreativeCommonsConfirmed,
                        response.body()!!.drinks[0].dateModified,
                        0,
                        ""
                    )




                    favDrinksList.add(drink)
                    favDrinksDataStore.saveNewDrink(drinkId.value)


                }
            }
        }
    }


    // uses id value stored in currentlyViewedId
    fun retrieveDrinkInfo(){

        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getCocktailById(currentlyViewedDrinkId)
            } catch (e: IOException) {
                //Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                //Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {

                //collects id, name and pic
                drinkId.value = response.body()!!.drinks[0].idDrink.toString()

                drinkName.value = response.body()!!.drinks[0].strDrink.toString()
                imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb.toString()

                //collects ingredients
                gatherIngredients(response)
                gatherMeasurements(response)

                //collect instructions
                instructions.value = response.body()!!.drinks[0].strInstructions.toString()


            }
        }
    }


    fun retrieveRandomDrink(){

        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getRandomCocktail()
            } catch (e: IOException) {
                //Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                //Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {

                //collects id, name and pic
                drinkId.value = response.body()!!.drinks[0].idDrink.toString()
                drinkName.value = response.body()!!.drinks[0].strDrink.toString()
                imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb.toString()
                currentlyViewedDrinkId = drinkId.value

                //collects ingredients
                gatherIngredients(response)
                gatherMeasurements(response)

                //collect instructions
                instructions.value = response.body()!!.drinks[0].strInstructions.toString()



            }
        }
    }




    suspend fun deleteFromList(drinkId: String){
        favDrinksList.remove(favDrinksList.find { it.idDrink == drinkId })
        favDrinksDataStore.removeDrink(drinkId)
    }

    //@Composable
    fun isInList(id: String) : Boolean{
        favDrinksList.forEach {
            if (id == it.idDrink) {
                return true
            }
        }
        return false
    }



    private fun gatherIngredients(response: Response<Drinks>){
        ingredients.clear()

        if(response.body()!!.drinks[0].strIngredient1 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient1.toString())
        if(response.body()!!.drinks[0].strIngredient2 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient2.toString())
        if(response.body()!!.drinks[0].strIngredient3 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient3.toString())
        if(response.body()!!.drinks[0].strIngredient4 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient4.toString())
        if(response.body()!!.drinks[0].strIngredient5 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient5.toString())
        if(response.body()!!.drinks[0].strIngredient6 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient6.toString())
        if(response.body()!!.drinks[0].strIngredient7 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient7.toString())
        if(response.body()!!.drinks[0].strIngredient8 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient8.toString())
        if(response.body()!!.drinks[0].strIngredient9 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient9.toString())
        if(response.body()!!.drinks[0].strIngredient10 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient10.toString())
        if(response.body()!!.drinks[0].strIngredient11 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient11.toString())
        if(response.body()!!.drinks[0].strIngredient12 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient12.toString())
        if(response.body()!!.drinks[0].strIngredient13 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient13.toString())
        if(response.body()!!.drinks[0].strIngredient14 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient14.toString())
        if(response.body()!!.drinks[0].strIngredient15 != null)
            ingredients.add(response.body()!!.drinks[0].strIngredient15.toString())
    }

    private fun gatherMeasurements(response: Response<Drinks>) {
        measurements.clear()


        if(response.body()!!.drinks[0].strMeasure1 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure1.toString())
        if(response.body()!!.drinks[0].strMeasure2 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure2.toString())
        if(response.body()!!.drinks[0].strMeasure3 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure3.toString())
        if(response.body()!!.drinks[0].strMeasure4 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure4.toString())
        if(response.body()!!.drinks[0].strMeasure5 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure5.toString())
        if(response.body()!!.drinks[0].strMeasure6 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure6.toString())
        if(response.body()!!.drinks[0].strMeasure7 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure7.toString())
        if(response.body()!!.drinks[0].strMeasure8 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure8.toString())
        if(response.body()!!.drinks[0].strMeasure9 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure9.toString())
        if(response.body()!!.drinks[0].strMeasure10 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure10.toString())
        if(response.body()!!.drinks[0].strMeasure11 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure11.toString())
        if(response.body()!!.drinks[0].strMeasure12 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure12.toString())
        if(response.body()!!.drinks[0].strMeasure13 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure13.toString())
        if(response.body()!!.drinks[0].strMeasure14 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure14.toString())
        if(response.body()!!.drinks[0].strMeasure15 != null)
            measurements.add(response.body()!!.drinks[0].strMeasure15.toString())
    }




    suspend fun setList(favDrinksFlow: Flow<ListOfDrinks>) {
        favDrinksFlow.collect{ drink ->
            val list = drink.drinkIDList
            list.forEach{
                addDrinkById(it)
            }
        }
    }

    lateinit var favDrinksDataStore: FavDrinksDataStore
    fun setDataStore(favDrinksDataStore: FavDrinksDataStore) {
        this.favDrinksDataStore = favDrinksDataStore
    }


}


