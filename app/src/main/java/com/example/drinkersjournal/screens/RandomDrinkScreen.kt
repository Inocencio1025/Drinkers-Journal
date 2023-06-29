package com.example.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
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
import com.example.drinkersjournal.*
import com.example.drinkersjournal.data.BottomNavItem
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomDrinkScreen (navController: NavController) {

    // Boolean for if currently viewed drink is in favorites list
    var isInList by remember { mutableStateOf(false) }
    isInList = DrinkersInfo.isInList(DrinkersInfo.drinkId.value)

    // For coroutines
    var context = LocalContext.current
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
