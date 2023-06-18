package com.example.drinkersjournal

import android.util.Log
import android.widget.PopupWindow
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

private var hasRating = false
private var hasRatingText = false

@Composable
fun DrinkDetailsScreen (navController: NavController) {

    // Searches drink with ID that is currently stored in DrinkersInfo
    // and stores retrieved info in DrinkersInfo
    DrinkersInfo.retrieveDrinkInfo()

    // sets up content to display
    SetContent(navController)
}

@Composable
private fun SetContent(navController: NavController) {

    // renders background image
    SetBackgroundImage()

    Spacer(modifier = Modifier.height(20.dp))


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // drink image
        CreateDrinkImage(DrinkersInfo.imageUrlStr.value)

        // drink name
        CreateNameText(nameStr = DrinkersInfo.drinkName.value)

        // display user thoughts and rating if user has input any
        CreateRatingText(DrinkersInfo.drinkList.find { it.idDrink == DrinkersInfo.currentlyViewedDrinkId }?.ratingText.toString())
        CreateRating(DrinkersInfo.drinkList.find { it.idDrink == DrinkersInfo.currentlyViewedDrinkId }?.rating.toString())


        // the Buttons
        CreateButtons(navController)

        // display ingredients text, with some conditionals
        var i = 0
        DrinkersInfo.ingredients.forEach {
            if (DrinkersInfo.measurements.getOrNull(i) != null)
                CreateIngredientText(ingStr = DrinkersInfo.measurements[i] + " " + it)
            else {
                CreateIngredientText(ingStr = it)
            }
            i++
        }

        // display instructions
        CreateInstructionText(instrStr = DrinkersInfo.instructions.value)
    }
}


// ------------------Composables for drink display----------------------------------------------//


@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
fun CreateDrinkImage(img: String) {

    Box(modifier = Modifier.padding(24.dp)){
        GlideImage(
            model = img,
            contentDescription = "",
            modifier = Modifier.height(300.dp)
        )
    }


}

@Composable
fun CreateNameText(nameStr: String){
    Text(
        text = nameStr,
        color = Color.White,
        fontSize = 40.sp,
        modifier = Modifier.layoutId("drinkName")
    )
    Spacer(modifier = Modifier.height(20.dp))
}



@Composable
fun CreateRatingText(ratingText: String) {
    if (ratingText == "" || ratingText == "null") {
        hasRatingText = false
        return
    }

    DrinkersInfo.ratingText.value = "\"" + ratingText + "\""
    Text(
        text = DrinkersInfo.ratingText.value,
        color = Color.Green,
        fontSize = 24.sp
    )
    hasRatingText = true
}

@Composable
fun CreateRating(rating: String) {
    if (rating == "0" || rating == "null") {
        hasRating = false
        return
    }

    DrinkersInfo.rating.value = rating
    Text(
        text = "Rating: ",
        color = Color.Yellow
    )
    Text(
        text = DrinkersInfo.rating.value,
        color = Color.White
    )
    Spacer(modifier = Modifier.height(10.dp))

    hasRating = true
}

@Composable
private fun CreateButtons(navController: NavController) {
    Row {

        //button to delete from list
        Button(
            onClick = {
                DrinkersInfo.deleteFromList()
                navController.popBackStack()
            }
        )
        {
            Text(text = "Remove From List")
        }


        Spacer(modifier = Modifier.width(8.dp))

        //button for rating
        Button(onClick = {

        }) {
            if(hasRating || hasRatingText)
                Text(text = "Edit Rating")
            else
                Text(text = "Add Rating")
        }
    }
}



@Composable
fun CreateIngredientText(ingStr: String) {
    Text(
        text = ingStr,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier
            .layoutId("ingredients")
            .padding(4.dp)
    )
}

@Composable
fun CreateInstructionText(instrStr: String){
    Spacer(modifier = Modifier.height(30.dp))

    Text(
        text = "Directions:",
        color = Color.Red,
        fontSize = 20.sp
    )

    Text(
        text = instrStr,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp )
    )
}

