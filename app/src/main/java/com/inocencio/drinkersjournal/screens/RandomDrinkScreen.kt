package com.inocencio.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.inocencio.drinkersjournal.*
import com.inocencio.drinkersjournal.data.DisplayDrink
import com.inocencio.drinkersjournal.ui.theme.topBarFont
import com.inocencio.drinkersjournal.util.DrinkersInfo
import kotlinx.coroutines.launch



private val isInList = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomDrinkScreen (navController: NavController) {

    isInList.value = false
    LaunchedEffect(Unit){
        isInList.value = DrinkersInfo.getInfoRandomDrink()
    }

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
        floatingActionButton = { CreateFAB() },
        bottomBar = { CreateBottomNavBar(navController) },
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
            }
        }
    )
}

@Composable
private fun CreateFAB() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            val id = DisplayDrink.drinkId.value
            coroutineScope.launch {
                if (!isInList.value) {
                    DrinkersInfo.addDrinkById(id = id, "", "0")
                    isInList.value = !isInList.value
                    Toast.makeText(
                        context,
                        DisplayDrink.drinkName.value + " added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    DrinkersInfo.deleteFromList(id)
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
}


