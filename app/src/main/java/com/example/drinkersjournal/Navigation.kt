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
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){

        // Home Screen
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }

        // View list of tried drinks
        composable(route = Screen.ViewListScreen.route){
           ViewListScreen(navController)
        }

        // View a drink at random
        composable(route = Screen.RandomDrinkScreen.route){
            RandomDrinkScreen()
        }

        // View drink details
        composable(route = Screen.DrinkDetailsScreen.route){
            DrinkDetailsScreen(navController)
        }

        // Browse Drinks Screen
        composable(route = Screen.BrowseDrinksScreen.route){
            BrowseDrinksScreen()
        }


    }
}

