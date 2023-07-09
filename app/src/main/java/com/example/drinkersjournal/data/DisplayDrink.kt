package com.example.drinkersjournal.data

import androidx.compose.runtime.mutableStateOf

object DisplayDrink {
    var imageUrlStr = mutableStateOf("")
    var drinkId = mutableStateOf("")
    var drinkName = mutableStateOf("")
    var instructions = mutableStateOf("")
    var ingredients = mutableListOf<String> ()
    var measurements = mutableListOf<String> ()
    var rating = mutableStateOf("")
    var ratingText = mutableStateOf("")

}