package com.example.drinkersjournal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route = Screen.ViewListScreen.route){

           ViewListScreen()
        }
    }
}

@Composable
fun MainScreen(navController: NavController){
    


    // renders background image
    Image(painter = painterResource(id = R.drawable.ic_temporary_home_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Column() {

        // renders name of app on top of home screen
        Image(
            painter = painterResource(id = R.drawable.ic_title),
            contentDescription = "Title Line 1",
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.1f))

        // All buttons on home screen
        Button(
            onClick = {},
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
fun ViewListScreen(){
    Text(text = "Fuck you")
}