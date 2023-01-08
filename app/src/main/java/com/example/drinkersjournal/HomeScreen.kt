package com.example.drinkersjournal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController




@Composable
fun HomeScreen(navController: NavController){

    SetBackgroundImage()

    Column() {

        // renders name of app on top of home screen
        Image(
            painter = painterResource(id = R.drawable.ic_title),
            contentDescription = "Title",
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.1f))

        // All buttons on home screen
        Button(
            onClick = {navController.navigate(Screen.RandomDrinkScreen.route)},
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Try Random Drink")
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Browse Drinks")
        }

        Button(
            onClick = {navController.navigate(Screen.ViewListScreen.route)},
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Your Drinker's List")
        }
    }
}

@Composable
fun SetBackgroundImage(){
    Image(
        painter = painterResource(id = R.drawable.ic_temporary_home_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}
