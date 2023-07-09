package com.example.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drinkersjournal.*
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.data.DisplayDrink
import com.example.drinkersjournal.ui.theme.topBarFont
import kotlinx.coroutines.launch

private var isInList = mutableStateOf(false)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomDrinkScreen (navController: NavController) {
    isInList.value = false

    LaunchedEffect(Unit){
        isInList.value = DrinkersInfo.retrieveRandomDrink()
    }

    // For coroutines
    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Random Drink",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = topBarFont)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
            )
        },
        content = { paddingValues ->
            SetBackgroundImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                // drink image
                CreateDrinkImage(DisplayDrink.imageUrlStr.value)

                // drink name
                CreateNameText(nameStr = DisplayDrink.drinkName.value)

                // display ingredients text, with some conditionals
                var i = 0
                DisplayDrink.ingredients.forEach {
                    if (DisplayDrink.measurements.getOrNull(i) != null)
                        CreateIngredientText(ingStr = DisplayDrink.measurements[i] + " " + it)
                    else {
                        CreateIngredientText(ingStr = it)
                    }
                    i++
                }

                // display instructions
                CreateInstructionText(instrStr = DisplayDrink.instructions.value)
                CreateBottomSpace()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        if (!isInList.value) {
                            DrinkersInfo.addDrinkById(id = DisplayDrink.drinkId.value,"","0")
                            isInList.value = !isInList.value
                            Toast.makeText(
                                context,
                                DisplayDrink.drinkName.value + " added to favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            DrinkersInfo.deleteFromList(DisplayDrink.drinkId.value)
                            isInList.value = !isInList.value
                            Toast.makeText(
                                context,
                                DisplayDrink.drinkName.value + " removed from favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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
                onItemClick = { navController.navigate(it.route) },


            )
        }
    )
}




