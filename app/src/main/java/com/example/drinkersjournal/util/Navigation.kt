package com.example.drinkersjournal.util



import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drinkersjournal.*
import com.example.drinkersjournal.screens.BrowseDrinksScreen
import com.example.drinkersjournal.screens.DrinkDetailsScreen
import com.example.drinkersjournal.screens.HomeScreen
import com.example.drinkersjournal.screens.RandomDrinkScreen

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
           FavoriteDrinksScreen(navController)
        }

        // View a drink at random
        composable(route = Screen.RandomDrinkScreen.route){

            RandomDrinkScreen(navController)

        }

        // View drink details
        composable(route = Screen.DrinkDetailsScreen.route){
            DrinkDetailsScreen(navController)
        }

        // Browse Drinks Screen
        composable(route = Screen.BrowseDrinksScreen.route){
            BrowseDrinksScreen(navController)
        }

        //list of drinks by ingredient
        composable(route = Screen.DrinkListByIngredientScreen.route){
            DrinkListByIngredientScreen(navController)
        }

    }
}

