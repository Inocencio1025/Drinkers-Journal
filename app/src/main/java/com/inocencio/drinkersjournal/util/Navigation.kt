package com.inocencio.drinkersjournal.util



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inocencio.drinkersjournal.data.Screen
import com.inocencio.drinkersjournal.screens.*

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
            BrowseIngredientsScreen(navController)
        }

        //list of drinks by ingredient
        composable(route = Screen.DrinkListByIngredientScreen.route){
            DrinkListByIngredientScreen(navController)
        }
    }
}

