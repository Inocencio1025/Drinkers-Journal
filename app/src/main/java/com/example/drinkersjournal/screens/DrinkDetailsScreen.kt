package com.example.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.BottomNavigationBar
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.SetBackgroundImage
import com.example.drinkersjournal.data.BottomNavItem
import kotlinx.coroutines.launch

private var hasRating = false
private var hasRatingText = false
var stringRating = mutableStateOf("")
var isRating = mutableStateOf(false)



@Composable
fun DrinkDetailsScreen (navController: NavController) {

    // Searches drink with ID that is currently stored in DrinkersInfo
    // and stores retrieved info in DrinkersInfo
    DrinkersInfo.retrieveDrinkInfo()

    // sets up content to display
    SetContent(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetContent(navController: NavController) {


    // Boolean for if currently viewed drink is in favorites list
    var isInList by remember { mutableStateOf(false) }
    isInList = DrinkersInfo.isInList(DrinkersInfo.drinkId.value)


    // For coroutines
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        content = { paddingValues ->
            SetBackgroundImage()
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, bottom = paddingValues.calculateBottomPadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally)
            {

                // drink image
                CreateDrinkImage(DrinkersInfo.imageUrlStr.value)

                // drink name
                CreateNameText(nameStr = DrinkersInfo.drinkName.value)

                //Displays Rating
                CreateRatingText()

                //Add Button for rating
                if(isInList){
                    Button(onClick = {
                        // add rating text to object here
                        isRating.value = !isRating.value

                    }) {
                        if(isRating.value){
                            Text(text = "Save Rating")
                        }
                        else if(hasRating || hasRatingText)
                            Text(text = "Edit Rating")
                        else
                            Text(text = "Add Rating")
                    }
                }

                // Displays ingredients
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
                CreateBottomSpace()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        if (!isInList) {
                            DrinkersInfo.addDrinkById(id = DrinkersInfo.drinkId.value)
                            isInList = !isInList
                            Toast.makeText(
                                context,
                                DrinkersInfo.drinkName.value + " added to list",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            DrinkersInfo.deleteFromList(DrinkersInfo.drinkId.value)
                            isInList = !isInList
                            Toast.makeText(
                                context,
                                DrinkersInfo.drinkName.value + " removed from list",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    isInList = DrinkersInfo.isInList(DrinkersInfo.drinkId.value)
                },
                contentColor = Color.White
            ) {
                if (!isInList) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to Favorites"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to Favorites"
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Try Drink",
                        route = "random_drink_screen",
                        icon = Icons.Default.Refresh
                    ),
                    BottomNavItem(
                        name = "Browse",
                        route = "browse_drinks_screen",
                        icon = Icons.Default.List
                    ),
                    BottomNavItem(
                        name = "Favorites",
                        route = "view_list_screen",
                        icon = Icons.Default.Favorite
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    )
}


// ------------------Composable for drink display----------------------------------------------//


@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
fun CreateDrinkImage(img: String) {

    Box(modifier = Modifier.padding(24.dp)){
        GlideImage(
            model = img,
            contentDescription = "",
            modifier = Modifier.height(200.dp)
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRatingText() {


    if(isRating.value){
        TextField(
            value = stringRating.value,
            onValueChange = { newText ->
                stringRating.value = newText
            }
        )
    } else{
        if (stringRating.value.isNotEmpty()){
            Text(text = "\"" + stringRating.value + "\"",
                color = Color.Green,
                fontSize = 24.sp)
        }
    }
    Spacer(modifier = Modifier.height(5.dp))

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
private fun CreateRateButton(isRating: Boolean) {
    //button for rating

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

@Composable
fun CreateBottomSpace(){ Spacer(modifier = Modifier.height(200.dp)) }