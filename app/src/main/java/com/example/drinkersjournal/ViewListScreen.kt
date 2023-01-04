package com.example.drinkersjournal



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import kotlin.random.Random

var i = 0
var drinkersList = mutableListOf<Drink>()



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewListScreen(){

    SetBackgroundImage()


    // for testing-------------------------------------------
    var testDrinks = listOf<Int>(11007,
        11028,
        11011,
        11012,
        11013,
        11014,
        11008,
        11009,
        11025,
        11022,
        11008)
    addTestDrinks(testDrinks)


    //-------------------------------------------------


    DisplayDrinkList()




}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayDrinkList(){


    LazyColumn() {
        itemsIndexed(drinkersList){ index, drink ->

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                GlideImage(
                    model = drink.strDrinkThumb.toString(),
                    contentDescription = "Drink image",
                    modifier = Modifier
                        .fillMaxHeight(1f)
                )

                Text(text = drink.strDrink.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .padding(vertical = 24.dp)
                        .background(color = Color.Red)

                )

                Text(text = drink.rating.toString(),
                    color = Color.White,
                    fontSize = 50.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Green))

            }







        }
    }


}

@Composable
fun addTestDrinks(id: List<Int>) {
    // add drinks by id



    id.forEach(){
        CoroutineScope(Dispatchers.Main).launch {

            val response = try {
                RetrofitInstance.api.getCocktailById(drinkID = it)
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
                    response.body()!!.drinks[0].dateModified,
                    Random.nextInt(0,10)
                )

                drinkersList.add(drink)

                //Log.d(TAG, drinkersList[i++].strDrink + " added to list")


            }

        }


    }

    @Composable
    fun SetBackgroundImage(){
        Image(
            painter = painterResource(id = R.drawable.ic_temporary_home_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
