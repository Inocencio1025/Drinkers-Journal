package com.example.drinkersjournal



import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
var i = 0
var drinkersList = mutableListOf<Drink>()

@Composable
fun ViewListScreen(){

    // for testing-------------------------------------------
    addTestDrink(11007)
    addTestDrink(11008)
    addTestDrink(11009)


    //-------------------------------------------------




}

fun addTestDrink(id: Int) {
    // add drinks by id

    CoroutineScope(Dispatchers.Main).launch {

        val response = try {
            RetrofitInstance.api.getCocktailById(drinkID = id)
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {




            //collect all info needed
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
                response.body()!!.drinks[0].dateModified
                )

            drinkersList.add(drink)

            Log.d(TAG, drinkersList[i++].strDrink + " added to list")

        } else
            Log.d(TAG, "FLLLAGGGGGGGGG")
    }
}
