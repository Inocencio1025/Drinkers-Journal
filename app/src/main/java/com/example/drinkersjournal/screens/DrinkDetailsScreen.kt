package com.example.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.ui.theme.drinkRatingTextFont
import kotlinx.coroutines.launch

var stringRating = mutableStateOf("")
var intRating = mutableStateOf(0)
var hasRating = mutableStateOf(false)
var isRating = mutableStateOf(false)
var isDoneRating = mutableStateOf(true)
var isInList = mutableStateOf(false)



@Composable
fun DrinkDetailsScreen (navController: NavController) {
    stringRating.value = ""
    intRating.value = 0
    isRating.value = false
    // Searches drink with ID that is currently stored in DrinkersInfo
    // and stores retrieved info in DrinkersInfo. isIntList is bool
    // for if retrieved drink is in fav list
    isInList.value = DrinkersInfo.retrieveDrinkInfo()

    // sets up content to display
    SetContent(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetContent(navController: NavController) {



    if(isInList.value) {
        var currentDrink = DrinkersInfo.userFavList.find { it.idDrink == DrinkersInfo.drinkId.value }
        if (currentDrink != null) {
            stringRating.value = currentDrink.ratingText.toString()
            intRating.value = currentDrink.rating
        }
    }




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
                CreateRating()

                //Add Button for rating
                AnimatedVisibility(visible = isInList.value) {
                    CreateRateButton()
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
                        if (!isInList.value) {
                            DrinkersInfo.addDrinkById(id = DrinkersInfo.drinkId.value)
                            isInList.value = !isInList.value
                            Toast.makeText(
                                context,
                                DrinkersInfo.drinkName.value + " added to favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            DrinkersInfo.deleteFromList(DrinkersInfo.drinkId.value)
                            isInList.value = !isInList.value
                            Toast.makeText(
                                context,
                                DrinkersInfo.drinkName.value + " removed from favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    isInList.value = DrinkersInfo.isInList(DrinkersInfo.drinkId.value)
                },
                contentColor = Color.Yellow,
                shape = CircleShape

            ) {
                if (!isInList.value) {
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
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name = "Favorites",
                        route = "view_list_screen",
                        icon = Icons.Default.List
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


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalAnimationApi::class) //for glideImage
@Composable
fun CreateDrinkImage(img: String) {

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    Box(modifier = Modifier.padding(24.dp)){
        AnimatedVisibility(visibleState = state, ) {
            GlideImage(
                model = img,
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .animateEnterExit(
                        enter = fadeIn(),
                        exit = fadeOut()
                    )
            )
        }

    }


}

@Composable
fun CreateNameText(nameStr: String){
    Text(
        text = nameStr,
        color = Color.White,
        fontSize = 40.sp,
        modifier = Modifier.layoutId("drinkName"),
        fontFamily = drinkNameFont
    )
    Spacer(modifier = Modifier.height(10.dp))
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRating() {
    //Text
    AnimatedVisibility(
        visible= stringRating.value != "" && !isRating.value,
        enter = slideInVertically () + expandVertically() + fadeIn(),
        exit = slideOutVertically () + shrinkVertically() + fadeOut()

        ){
            Text(text = "\"" + stringRating.value + "\"",
                color = Color.Green,
                fontSize = 24.sp,
                fontFamily = drinkRatingTextFont)
    }

    //Displaying the stars
    AnimatedVisibility(visible = intRating.value > 0 || isRating.value) {
        CreateRatingStars(
            maxRating = 5,
            currentRating = intRating.value,
            onRatingChanged = { newRating ->
                intRating.value = if (newRating == intRating.value) 0 else newRating
            }
        )
    }


    //TextBox
    AnimatedVisibility(visible = isRating.value){
        TextField(
            value = stringRating.value,
            onValueChange = { newText ->
                stringRating.value = newText
            }
        )
    }
}

@Composable
fun CreateRatingStars(
    maxRating: Int,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit
) {


    Row(Modifier.padding(8.dp)) {
        for (i in 1..maxRating) {
            val isFilled = i <= currentRating

            val starIcon = if (isFilled) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.Star
            }

            Icon(
                imageVector = starIcon,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onRatingChanged(i)
                    }
                    .padding(4.dp),
                tint = if (isFilled) Color.Yellow else Color.Yellow.copy(
                    alpha = .4f
                )
            )
        }
    }
}

@Composable
private fun CreateRateButton() {
    val currentDrink = DrinkersInfo.userFavList.find { it.idDrink == DrinkersInfo.drinkId.value }
    Button(
        modifier = Modifier.padding(vertical = 10.dp),
        onClick = {
            isRating.value = !isRating.value
            isDoneRating.value = !isDoneRating.value


            if (currentDrink != null) {
                DrinkersInfo.addRatingToDrinkByID(currentDrink, stringRating.value, intRating.value)

                //set flags
                stringRating.value = currentDrink.ratingText.toString()
                intRating.value = currentDrink.rating
                hasRating.value = currentDrink.hasRating()
            }
            if (currentDrink != null) {


            }
        }
    ) {
        if(isRating.value){
                Text(text = "Save Rating")
        }
        else if (currentDrink != null && currentDrink.hasRating())
            Text(text = "Edit Rating")
        else
            Text(text = "Add Rating")
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
        textDecoration = TextDecoration.Underline,
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