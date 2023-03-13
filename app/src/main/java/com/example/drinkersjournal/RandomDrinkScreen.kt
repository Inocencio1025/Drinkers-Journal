package com.example.drinkersjournal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
fun RandomDrinkScreen (navController: NavController) {

    // Calls to api and gathers the data to display
    DrinkersInfo.retrieveRandomDrink()

    // sets up content to display
    SetContent(navController)
}

// Create methods are in DrinkDetailsScreen
@Composable
private fun SetContent(navController: NavController) {



    Scaffold(
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
    ) {

        SetBackgroundImage()
        // start of content to display
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // drink image
            CreateDrinkImage(DrinkersInfo.imageUrlStr.value)

            // drink name
            CreateNameText(nameStr = DrinkersInfo.drinkName.value)



            // the unique buttons for this screen
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


}

// buttons: randomize drink, save drink
@Composable
private fun CreateButtons() {
    var context = LocalContext.current
    Row {
        // random drink button
        Button(
            onClick = { DrinkersInfo.retrieveRandomDrink()},
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Text(text = "Randomize Drink")
        }

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = {
                DrinkersInfo.addDrinkById(id = DrinkersInfo.drinkId.value)

                if(!DrinkersInfo.isInList(DrinkersInfo.drinkId.value))
                    Toast.makeText(context, "Added to list", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "Add To List")
        }
    }
}

