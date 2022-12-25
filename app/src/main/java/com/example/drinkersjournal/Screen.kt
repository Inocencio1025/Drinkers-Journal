package com.example.drinkersjournal

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ViewListScreen : Screen("view_list_screen")
}
