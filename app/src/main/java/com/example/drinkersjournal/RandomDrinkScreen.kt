package com.example.drinkersjournal

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "RandomDrinkScreen"
val text = mutableStateOf("No Drink Name")


@Composable
fun RandomDrinkScreen () {

    /*
    //background
    Image(
        painter = painterResource(id = R.drawable.ic_temporary_home_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column() {
        Text(text = )

        Image(
        )

    }

     */





    LaunchedEffect(true) {
        val response = try {
            RetrofitInstance.api.getRandomCocktail()
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
            return@LaunchedEffect
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return@LaunchedEffect
        }



        if (response.isSuccessful && response.body() != null) {

            //text = response.body()!!.drinks[0].strDrink
            //update viewmodel
            text.value = response.body()!!.drinks[0].strDrink
        }

    }

    Text(text = text.value)
    //get text from viewmodel
}








    
  

