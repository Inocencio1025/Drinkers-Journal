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
import androidx.compose.ui.text.style.TextAlign
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
    var testDrinks = listOf(
        11007,
        11028,
        11011,
        11012,
        11013,
        11014,
        11008,
        11009,
        11025,
        11022,
        11008,)
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                        //.background(Color.Green)
                ) {
                    Text(text = drink.strDrink.toString(),
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            //.background(color = Color.Red)
                    )
                    Text(text = drink.ratingText.toString(),
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            //.background(color = Color.Green)
                            .padding(horizontal = 10.dp)
                            .fillMaxSize()
                    )

                }


                Text(text = drink.rating.toString(),
                    textAlign = TextAlign.Justify,
                    color = Color.White,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .fillMaxWidth())

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
                    Random.nextInt(0,10),
                    "Tastes like ass Tast e as Tass like a Ta like s Tastes like ass Tastes like ass Tastes like ass Tastes like ass Tastes like assTa stes like ass"
                )

                drinkersList.add(drink)

                //Log.d(TAG, drinkersList[i++].strDrink + " added to list")


            }

        }


    }


}



