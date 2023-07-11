package com.inocencio.drinkersjournal.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inocencio.drinkersjournal.util.DrinkersInfo
import com.inocencio.drinkersjournal.R
import com.inocencio.drinkersjournal.data.Screen

@Composable
fun HomeScreen(navController: NavController){
    // DrinkersInfo is basically where all my data-retrieval functions happens
    DrinkersInfo.getInfoRandomDrink()
    // I made this before learning what a viewModel was, in the future
    // I will implement viewModels until DrinkersInfo should be gone entirely

    Surface() {
        SetBackgroundImage()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // App Logo
            CreateAppLogoImage(
                Modifier
                    .fillMaxHeight(.4f)
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            //All main menu buttons
            CreateMainMenuButton(label = "Browse Drinks", Screen.BrowseDrinksScreen.route, navController,
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
            CreateMainMenuButton(label = "Try Random Drink", Screen.RandomDrinkScreen.route, navController,
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 0.dp),
            )
            CreateMainMenuButton(label = "Favorites List", Screen.ViewListScreen.route, navController,
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
    }
}

@Composable
private fun CreateAppLogoImage(modifier : Modifier){
    Image(
        painter = painterResource(id = R.drawable.ic_title),
        contentDescription = "Title",
        modifier = modifier
    )
}

@Composable
private fun CreateMainMenuButton(label : String,route: String, navController: NavController, modifier : Modifier){
    val colors = listOf(Color.Transparent, MaterialTheme.colorScheme.tertiary, Color.Transparent)
    val gradient = Brush.horizontalGradient(colors = colors)

    val borderColors = listOf(Color.Transparent, MaterialTheme.colorScheme.onPrimary, Color.Transparent)
    val borderGradient = Brush.horizontalGradient(colors = borderColors)

    Divider(color = Color.Transparent, thickness = 1.dp, modifier = Modifier.background(borderGradient))
    Button(
        onClick = {navController.navigate(route)},
        modifier = modifier.background(gradient),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.displayMedium) {
                Text(
                    text = label,
                    fontSize = 32.sp
                )
            }
        }
    }
    Divider(color = Color.Transparent, thickness = 1.dp, modifier = Modifier.background(borderGradient))
}
