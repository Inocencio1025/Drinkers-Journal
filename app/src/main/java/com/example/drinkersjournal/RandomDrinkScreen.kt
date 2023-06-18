package com.example.drinkersjournal

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun RandomDrinkScreen (navController: NavController) {

    // Calls to api and gathers the data to display



    // sets up content to display
    SetContent(navController)
}

// Create methods are in DrinkDetailsScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetContent(navController: NavController) {

    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var visible by remember { mutableStateOf(false) }
    var isInList by remember { mutableStateOf(false) }
    isInList = DrinkersInfo.isInList()


    Scaffold(
        content = { paddingValues ->
            SetBackgroundImage()
            // start of content to display
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, bottom = paddingValues.calculateBottomPadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {




                CreateDrinkImage(DrinkersInfo.imageUrlStr.value)






                // drink name
                CreateNameText(nameStr = DrinkersInfo.drinkName.value)



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



                CreateBottomSpace()



            }




        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    if (!isInList) {
                        coroutineScope.launch {
                            Toast.makeText(context, DrinkersInfo.drinkName.value + " added to list", Toast.LENGTH_SHORT).show()
                            DrinkersInfo.addDrinkById(id = DrinkersInfo.currentlyViewedDrinkId)
                            isInList = !isInList
                        }
                    }
                    else {
                        DrinkersInfo.deleteFromList()
                        isInList = !isInList

                    }

                    isInList = DrinkersInfo.isInList()



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
@Composable
fun CreateBottomSpace(){

    Spacer(modifier = Modifier.height(200.dp))
}



/*
// buttons: randomize drink, save drink
@Composable
private fun CreateButtons() {


    Box {
        // random drink button
        Button(
            onClick = {
                DrinkersInfo.retrieveRandomDrink()

                      },
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Text(text = "Randomize Drink")
        }
    }
}

 */
