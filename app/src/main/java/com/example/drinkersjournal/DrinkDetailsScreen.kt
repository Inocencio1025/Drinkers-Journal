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


@Composable
fun DrinkDetailsScreen () {

    // Searches drink with ID that is currently stored in DrinkersInfo
    // and stores retrieved info in DrinkersInfo
    DrinkersInfo.retrieveDrink()

    // sets up content to display
    SetContent()
}

@Composable
private fun SetContent() {

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
        CreateDrinkImage()

        // drink name
        CreateNameText(nameStr = DrinkersInfo.drinkName.value)

        // display user thoughts and rating if any


        // the Buttons
        CreateButtons()

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
fun CreateDrinkImage() {
    Spacer(modifier = Modifier.height(20.dp))

    GlideImage(
        model = DrinkersInfo.imageUrlStr.value,
        contentDescription = "Picture of Random Drink",
        modifier = Modifier.fillMaxSize(1f)
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
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun CreateButtons() {

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

