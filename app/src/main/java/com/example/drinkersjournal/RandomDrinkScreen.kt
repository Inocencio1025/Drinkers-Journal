package com.example.drinkersjournal


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response


var imageUrlStr = mutableStateOf("Fetching drink...")
var drinkName = mutableStateOf("")
var instructions = mutableStateOf("")
var ingredients: MutableList<String> by mutableStateOf(mutableListOf())

//var drinkName4 = mutableStateOf("")
//var drinkName5 = mutableStateOf("")


@Composable
fun RandomDrinkScreen () {

    // retrieves info before loading onto screen
    retrieveRandomDrink()

    // displays the compose components
    SetContent()

}

@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
fun SetContent() {

    // renders background image
    setBackgroundImage()

    // Column Start
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        //drink image
        GlideImage(
            model = imageUrlStr.value,
            contentDescription = "Picture of Random Drink",

            modifier = Modifier.fillMaxWidth()
        )

        //drink name
        Text(
            text = drinkName.value,
            color = Color.White,
            fontSize = 48.sp,
            )


        Spacer(modifier = Modifier.height(5.dp))

        // button for displaying the next drink
        Button(onClick = { retrieveRandomDrink() }) {
            Text(text = "Try Another")
        }

        // display ingredients text
        ingredients.forEach{
            CreateIngredientText(ingStr = it)
        }

        CreateInstructionText(instrStr = instructions.value)

    }
}

// Ingredient text
@Composable
fun CreateIngredientText(ingStr: String){
    Text(
        text = ingStr,
        color = Color.White,
        fontSize = 24.sp)
}

@Composable
fun CreateInstructionText(instrStr: String){
    Text(
        text = instrStr,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 36.dp, horizontal = 20.dp ))
}

fun retrieveRandomDrink(){

    CoroutineScope(Dispatchers.Main).launch {

        val response = try {
            RetrofitInstance.api.getRandomCocktail()
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {

            drinkName.value = response.body()!!.drinks[0].strDrink
            imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb

            //collects ingredients into private list
            gatherIngredients(response)

            instructions.value = response.body()!!.drinks[0].strInstructions


        }
    }
}

fun gatherIngredients(response: Response<Drinks>){
    ingredients.clear()

    if(response.body()!!.drinks[0].strIngredient1 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure1 + " " + response.body()!!.drinks[0].strIngredient1)
    if(response.body()!!.drinks[0].strIngredient2 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure2 + " " + response.body()!!.drinks[0].strIngredient2)
    if(response.body()!!.drinks[0].strIngredient3 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure3 + " " + response.body()!!.drinks[0].strIngredient3)
    if(response.body()!!.drinks[0].strIngredient4 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure4 + " " + response.body()!!.drinks[0].strIngredient4)
    if(response.body()!!.drinks[0].strIngredient5 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure5 + " " + response.body()!!.drinks[0].strIngredient5)
    if(response.body()!!.drinks[0].strIngredient6 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure6 + " " + response.body()!!.drinks[0].strIngredient6)
    if(response.body()!!.drinks[0].strIngredient7 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure7 + " " + response.body()!!.drinks[0].strIngredient7)
    if(response.body()!!.drinks[0].strIngredient8 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure8 + " " + response.body()!!.drinks[0].strIngredient8)
    if(response.body()!!.drinks[0].strIngredient9 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure9 + " " + response.body()!!.drinks[0].strIngredient9)
    if(response.body()!!.drinks[0].strIngredient10 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure10 + " " + response.body()!!.drinks[0].strIngredient10)
    if(response.body()!!.drinks[0].strIngredient11 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure11 + " " + response.body()!!.drinks[0].strIngredient11)
    if(response.body()!!.drinks[0].strIngredient12 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure12 + " " + response.body()!!.drinks[0].strIngredient12)
    if(response.body()!!.drinks[0].strIngredient13 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure13 + " " + response.body()!!.drinks[0].strIngredient13)
    if(response.body()!!.drinks[0].strIngredient14 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure14 + " " + response.body()!!.drinks[0].strIngredient14)
    if(response.body()!!.drinks[0].strIngredient15 != null)
        ingredients.add(response.body()!!.drinks[0].strMeasure15 + " " + response.body()!!.drinks[0].strIngredient15)


}

@Composable
fun setBackgroundImage(){
    Image(
        painter = painterResource(id = R.drawable.ic_temporary_home_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}










    
  

