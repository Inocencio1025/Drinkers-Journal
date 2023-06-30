package com.example.drinkersjournal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.R
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.util.Screen


@Composable
fun HomeScreen(navController: NavController){

    DrinkersInfo.retrieveRandomDrink()




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


            //All amin menu buttons
            CreateMainMenuButton(label = "Try Random Drink", Screen.RandomDrinkScreen.route, navController,
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 0.dp),
            )

            CreateMainMenuButton(label = "Browse Drinks", Screen.BrowseDrinksScreen.route, navController,
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
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
fun CreateAppLogoImage(modifier : Modifier){
    Image(
        painter = painterResource(id = R.drawable.ic_title),
        contentDescription = "Title",
        modifier = modifier
    )
}

@Composable
fun CreateMainMenuButton(label : String,route: String, navController: NavController, modifier : Modifier){

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

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        tonalElevation = 5.dp
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                //selectedContentColor = Color.Yellow,
                //unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name)
                        if(selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp)
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun SetBackgroundImage(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_temporary_home_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
