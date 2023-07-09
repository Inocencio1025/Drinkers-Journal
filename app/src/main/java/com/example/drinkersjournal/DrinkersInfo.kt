package com.example.drinkersjournal

import android.content.ContentValues.TAG
import android.util.Log
import com.example.drinkersjournal.data.DisplayDrink
import com.example.drinkersjournal.data.Drink
import com.example.drinkersjournal.data.DrinkByIngredients
import com.example.drinkersjournal.data.Drinks
import com.example.drinkersjournal.screens.drinksByIngredient
import com.example.drinkersjournal.util.FavDrinksDataStore
import com.example.drinkersjournal.util.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object DrinkersInfo {

    // list of favorite drinks saved by user
    val userFavList : MutableList<Drink> = mutableListOf()

    // DataStore for saving userFavList for when app closes
    private lateinit var favDrinksDataStore: FavDrinksDataStore








    //this string is specifically for the topAppBar to read ingredient name
    //probably a better way to do this but I just wanna be done with this project
    var ingredientAppBarTextHolder = ""


    fun setIngredientForSearch(ingredient :String){
        drinksByIngredient.clear()
        retrieveDrinksByIngredient(ingredient)
        ingredientAppBarTextHolder = ingredient
    }

    // adds drink to user list
    suspend fun addDrinkById(id: String, rating: String, ratingNum: String, ){
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
                        ratingNum,
                        rating
                    )
                    userFavList.add(drink)
                    val newProtoDrink = ProtoDrink.newBuilder().setID(id).setRating(rating).setRatingNum(ratingNum)
                    favDrinksDataStore.saveNewDrink(newProtoDrink)
                }
            }
        }
    }

    private fun retrieveDrinksByIngredient(ingredient: String){
        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getDrinksByIngredient(ingredient)
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {

                response.body()!!.drinks.forEach {
                    val drinkByIngredients = DrinkByIngredients(
                        it.idDrink,
                        it.strDrink,
                        it.strDrinkThumb
                    )
                    drinksByIngredient.add(drinkByIngredients)
                    Log.e("LOOK", ":)")

                }
            }
            else {
                Log.e("LOOK", ":(")
            }
        }
    }

    // uses id value stored in drinkId
    fun retrieveDrinkInfo(): Boolean {
        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getCocktailById(DisplayDrink.drinkId.value)
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {

                //collects id, name and pic
                DisplayDrink.drinkId.value = response.body()!!.drinks[0].idDrink

                DisplayDrink.drinkName.value = response.body()!!.drinks[0].strDrink.toString()
                DisplayDrink.imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb.toString()

                //collects ingredients
                gatherIngredients(response)
                gatherMeasurements(response)

                //collect instructions
                DisplayDrink.instructions.value = response.body()!!.drinks[0].strInstructions.toString()

            }
        }

        return isInList(DisplayDrink.drinkId.value)
    }

    fun retrieveRandomDrink(): Boolean {
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
                DisplayDrink.drinkId.value = response.body()!!.drinks[0].idDrink
                DisplayDrink.drinkName.value = response.body()!!.drinks[0].strDrink.toString()
                DisplayDrink.imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb.toString()
                //currentlyViewedDrinkId = drinkId.value

                //collects ingredients
                gatherIngredients(response)
                gatherMeasurements(response)

                //collect instructions
                DisplayDrink.instructions.value = response.body()!!.drinks[0].strInstructions.toString()
            }
        }
        return isInList(DisplayDrink.drinkId.value)
    }

    suspend fun deleteFromList(drinkId: String){
        userFavList.remove(userFavList.find { it.idDrink == drinkId })
        favDrinksDataStore.removeDrink(drinkId)
    }

    private fun isInList(id: String) : Boolean{
        userFavList.forEach {
            if (id == it.idDrink) {
                return true
            }
        }
        return false
    }

    // for getting user favorite drink list from dataStore
    suspend fun setList(favDrinksFlow: Flow<ListOfDrinks>) {

        favDrinksFlow.collect{ drink ->
            val list = drink.protoDrinkList
            list.forEach{
                addDrinkById(it.id,  it.rating, it.ratingNum)

                Log.e("LOOK", it.id + " -------- " + it.ratingNum)
            }
        }
    }

    // not sure if this is the correct way to do this...
    fun setDataStore(favDrinksDataStore: FavDrinksDataStore) {
        this.favDrinksDataStore = favDrinksDataStore
    }

    suspend fun addRatingToDrinkByID(drinkID: String, ratingString: String, ratingNum: Int) {
        val editedDrink = userFavList.find { DisplayDrink.drinkId.value == it.idDrink }
        if (editedDrink != null) {
            editedDrink.ratingText = ratingString
            editedDrink.rating = ratingNum.toString()
        }

        DisplayDrink.ratingText.value = ratingString
        DisplayDrink.rating.value = ratingNum.toString()

        favDrinksDataStore.removeDrink(drinkID)

        val newProtoDrink = ProtoDrink.newBuilder()
            .setID(drinkID)
            .setRating(ratingString)
            .setRatingNum(ratingNum.toString())
        favDrinksDataStore.saveNewDrink(newProtoDrink)
    }

    suspend fun clearList(){
        userFavList.clear()
        favDrinksDataStore.clearListOfDrinks()
    }

    // used for api calls
    private fun gatherIngredients(response: Response<Drinks>){
        DisplayDrink.ingredients.clear()

        if(response.body()!!.drinks[0].strIngredient1 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient1.toString())
        if(response.body()!!.drinks[0].strIngredient2 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient2.toString())
        if(response.body()!!.drinks[0].strIngredient3 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient3.toString())
        if(response.body()!!.drinks[0].strIngredient4 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient4.toString())
        if(response.body()!!.drinks[0].strIngredient5 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient5.toString())
        if(response.body()!!.drinks[0].strIngredient6 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient6.toString())
        if(response.body()!!.drinks[0].strIngredient7 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient7.toString())
        if(response.body()!!.drinks[0].strIngredient8 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient8.toString())
        if(response.body()!!.drinks[0].strIngredient9 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient9.toString())
        if(response.body()!!.drinks[0].strIngredient10 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient10.toString())
        if(response.body()!!.drinks[0].strIngredient11 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient11.toString())
        if(response.body()!!.drinks[0].strIngredient12 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient12.toString())
        if(response.body()!!.drinks[0].strIngredient13 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient13.toString())
        if(response.body()!!.drinks[0].strIngredient14 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient14.toString())
        if(response.body()!!.drinks[0].strIngredient15 != null)
            DisplayDrink.ingredients.add(response.body()!!.drinks[0].strIngredient15.toString())
    }

    private fun gatherMeasurements(response: Response<Drinks>) {
        DisplayDrink.measurements.clear()

        if(response.body()!!.drinks[0].strMeasure1 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure1.toString())
        if(response.body()!!.drinks[0].strMeasure2 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure2.toString())
        if(response.body()!!.drinks[0].strMeasure3 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure3.toString())
        if(response.body()!!.drinks[0].strMeasure4 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure4.toString())
        if(response.body()!!.drinks[0].strMeasure5 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure5.toString())
        if(response.body()!!.drinks[0].strMeasure6 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure6.toString())
        if(response.body()!!.drinks[0].strMeasure7 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure7.toString())
        if(response.body()!!.drinks[0].strMeasure8 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure8.toString())
        if(response.body()!!.drinks[0].strMeasure9 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure9.toString())
        if(response.body()!!.drinks[0].strMeasure10 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure10.toString())
        if(response.body()!!.drinks[0].strMeasure11 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure11.toString())
        if(response.body()!!.drinks[0].strMeasure12 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure12.toString())
        if(response.body()!!.drinks[0].strMeasure13 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure13.toString())
        if(response.body()!!.drinks[0].strMeasure14 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure14.toString())
        if(response.body()!!.drinks[0].strMeasure15 != null)
            DisplayDrink.measurements.add(response.body()!!.drinks[0].strMeasure15.toString())
    }
}


