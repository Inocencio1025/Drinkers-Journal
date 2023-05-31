package com.example.drinkersjournal.ui.fav_drink_list

import androidx.lifecycle.ViewModel
import com.example.drinkersjournal.data.DrinkRepository
import com.example.drinkersjournal.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class FavoriteDrinkViewModel @Inject constructor(
    private val repository: DrinkRepository
): ViewModel() {

    val favDrinks = repository.getFavoriteDrinks()

    val _uiEvent = Channel<UiEvent> {  }
    val uiEvent = _uiEvent.receiveAsFlow()

}