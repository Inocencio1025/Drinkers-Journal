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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
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
var measurements: MutableList<String> by mutableStateOf(mutableListOf())




@Composable
fun RandomDrinkScreen () {





    // renders background image
    setBackgroundImage()

    // gets content, which then recomposes on screen
    retrieveRandomDrink()

    // sets up content to display
    setContent()








}

@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
fun setContent() {


    /*   Attempts to learn constrain layout
    val constraints = ConstraintSet() {
        val drinkImage = createRefFor("drinkImage")
        val drinkName = createRefFor("drinkName")
        val measurements = createRefFor("measurements")
        val ingredients = createRefFor("ingredients")
        val instructions = createRefFor("instructions")

        constrain(drinkImage) {
            top.linkTo(parent.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(drinkName) {
            top.linkTo(drinkImage.bottom)
        }
        constrain(measurements) {
            top.linkTo(drinkName.bottom)
            start.linkTo(parent.start)
        }
        constrain(ingredients) {
            top.linkTo(drinkName.bottom)
            end.linkTo(parent.end)

        }
        constrain(instructions) {
            top.linkTo(ingredients.bottom)
        }
    }
    
    ConstraintLayout(constraints, modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {

        // constraint layout contents
        GlideImage(
            model = imageUrlStr.value,
            contentDescription = "Picture of Random Drink",
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("drinkImage")
        )

        CreateNameText(nameStr = drinkName.value)


        // display ingredients text
        CreateMeasurementText(measureStr = "measurements[0]")
        CreateIngredientText(ingStr = "ingredients[0]")



        // display instructions
        CreateInstructionText(instrStr = instructions.value)




    }


     */


    // renders background image
    setBackgroundImage()
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


        // Start of content to display

        // drink image
        GlideImage(
            model = imageUrlStr.value,
            contentDescription = "Picture of Random Drink",
            modifier = Modifier.fillMaxSize()
        )




        // drink name
        CreateNameText(nameStr = drinkName.value)

        Spacer(modifier = Modifier.height(5.dp))

        // button for displaying the next drink
        Button(onClick = { retrieveRandomDrink() }) {
            Text(text = "Try Another")
        }

        // display ingredients text
        ingredients.forEach() {
            CreateIngredientText(it)
        }

        // display instructions
        CreateInstructionText(instrStr = instructions.value)


    }
}







// Ingredient text
@Composable
fun CreateIngredientText(ingStr: String) {
    // ingredient text
    Text(
        text = ingStr,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier
            .layoutId("ingredients")
            .padding(8.dp))

}

@Composable
fun CreateMeasurementText(measureStr: String){
    // measurement text
    Text(
        text = measureStr,
        color = Color.White,
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier.layoutId("measurements")
    )
}


@Composable
fun CreateNameText(nameStr: String){
    Text(
        text = nameStr,
        color = Color.White,
        fontSize = 40.sp,
        modifier = Modifier.layoutId("drinkName")
    )
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


        }




        //collects ingredients into private list in class
        gatherIngredients(response)
        gatherMeasurements(response)

        instructions.value = response.body()!!.drinks[0].strInstructions


    }
}


fun gatherIngredients(response: Response<Drinks>){
    ingredients.clear()

    if(response.body()!!.drinks[0].strIngredient1 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient1)
    if(response.body()!!.drinks[0].strIngredient2 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient2)
    if(response.body()!!.drinks[0].strIngredient3 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient3)
    if(response.body()!!.drinks[0].strIngredient4 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient4)
    if(response.body()!!.drinks[0].strIngredient5 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient5)
    if(response.body()!!.drinks[0].strIngredient6 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient6)
    if(response.body()!!.drinks[0].strIngredient7 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient7)
    if(response.body()!!.drinks[0].strIngredient8 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient8)
    if(response.body()!!.drinks[0].strIngredient9 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient9)
    if(response.body()!!.drinks[0].strIngredient10 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient10)
    if(response.body()!!.drinks[0].strIngredient11 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient11)
    if(response.body()!!.drinks[0].strIngredient12 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient12)
    if(response.body()!!.drinks[0].strIngredient13 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient13)
    if(response.body()!!.drinks[0].strIngredient14 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient14)
    if(response.body()!!.drinks[0].strIngredient15 != null)
        ingredients.add(response.body()!!.drinks[0].strIngredient15)
}

fun gatherMeasurements(response: Response<Drinks>) {
    measurements.clear()

    if(response.body()!!.drinks[0].strMeasure1 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure1)
    if(response.body()!!.drinks[0].strMeasure2 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure2)
    if(response.body()!!.drinks[0].strMeasure3 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure3)
    if(response.body()!!.drinks[0].strMeasure4 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure4)
    if(response.body()!!.drinks[0].strMeasure5 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure5)
    if(response.body()!!.drinks[0].strMeasure6 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure6)
    if(response.body()!!.drinks[0].strMeasure7 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure7)
    if(response.body()!!.drinks[0].strMeasure8 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure8)
    if(response.body()!!.drinks[0].strMeasure9 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure9)
    if(response.body()!!.drinks[0].strMeasure10 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure10)
    if(response.body()!!.drinks[0].strMeasure11 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure11)
    if(response.body()!!.drinks[0].strMeasure12 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure12)
    if(response.body()!!.drinks[0].strMeasure13 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure13)
    if(response.body()!!.drinks[0].strMeasure14 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure14)
    if(response.body()!!.drinks[0].strMeasure15 != null)
        checkMeasurement(response.body()!!.drinks[0].strMeasure15)
}

// created because some measurements come in as unintended strings
fun checkMeasurement(measure: String) {
    if(!measure.equals(null))
        measurements.add(measure)
    else
        measurements.add("")
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









    
  

