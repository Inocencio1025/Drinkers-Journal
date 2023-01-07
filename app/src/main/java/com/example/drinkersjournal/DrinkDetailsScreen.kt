package com.example.drinkersjournal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response


private var imageUrlStr = mutableStateOf("")
private var drinkId = mutableStateOf("")
private var drinkName = mutableStateOf("")
private var instructions = mutableStateOf("")
private var ingredients: MutableList<String> by mutableStateOf(mutableListOf())
private var measurements: MutableList<String> by mutableStateOf(mutableListOf())



@Composable
fun DrinkDetailsScreen () {

    // Calls to api and gathers the data to display
    retrieveDrink()

    // sets up content to display
    SetContent()

}


@Composable
private fun SetContent() {

    // renders background image
    SetBackgroundImage()


    // start of content to display
    Spacer(modifier = Modifier.height(20.dp))


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))


        // drink image
        CreateDrinkImage()


        // drink name
        CreateNameText(nameStr = drinkName.value)
        Spacer(modifier = Modifier.height(20.dp))



        // display user thoughts and rating if any


        // the Buttons
        CreateButtons()

        // display ingredients text, with some conditionals
        var i = 0
        ingredients.forEach {
            if (measurements.getOrNull(i) != null)
                CreateIngredientText(ingStr = measurements[i] + " " + it)
            else {
                CreateIngredientText(ingStr = it)
            }
            i++
        }

        Spacer(modifier = Modifier.height(30.dp))

        //displays word "Directions"
        Text(text = "Directions:",
            color = Color.Red,
            fontSize = 20.sp)

        // display instructions
        CreateInstructionText(instrStr = instructions.value)


    }
}

@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
private fun CreateDrinkImage() {
    GlideImage(
        model = imageUrlStr.value,
        contentDescription = "Picture of Random Drink",
        modifier = Modifier.fillMaxSize(1f)
    )
}



@Composable
private fun CreateButtons() {

}


@Composable
private fun CreateNameText(nameStr: String){
    Text(
        text = nameStr,
        color = Color.White,
        fontSize = 40.sp,
        modifier = Modifier.layoutId("drinkName")
    )
}


// Ingredient text
@Composable
private fun CreateIngredientText(ingStr: String) {
    // ingredient text
    Text(
        text = ingStr,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier
            .layoutId("ingredients")
            .padding(4.dp))

}


@Composable
private fun CreateInstructionText(instrStr: String){
    Text(
        text = instrStr,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp ))
}


private fun retrieveDrink(){



    CoroutineScope(Dispatchers.Main).launch {

        val response = try {
            RetrofitInstance.api.getCocktailById(DrinkersList.currentlyViewedDrink)
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
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




