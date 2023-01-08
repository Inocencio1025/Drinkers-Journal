package com.example.drinkersjournal

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object ViewListScreen : Screen("view_list_screen")
    object RandomDrinkScreen : Screen("random_drink_screen")
    object DrinkDetailsScreen : Screen("drink_details_screen")
    object BrowseDrinksScreen : Screen("browse_drinks_screen")
}
