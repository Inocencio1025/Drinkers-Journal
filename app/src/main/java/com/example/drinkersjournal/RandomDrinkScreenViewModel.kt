package com.example.drinkersjournal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

const val TAG = "RandomDrinkScreen"


class RandomDrinkScreenViewModel: ViewModel() {


    var drinkName: MutableState <String> = mutableStateOf("drinkName")
    var imageUrlStr: MutableState <String> = mutableStateOf("")




}