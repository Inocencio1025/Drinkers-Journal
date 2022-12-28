package com.example.drinkersjournal

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "RandomDrinkScreen"


@Composable
fun RandomDrinkScreen () {



    //var text = "NO CRASH!!!!!!!"
    //val scope = rememberCoroutineScope()  // scope.launch



    LaunchedEffect(true){
        val response = try {
            RetrofitInstance.api.getRandomCocktail()
        } catch (e: IOException){
            Log.e(TAG, "IOException, you might not have internet connection")
            return@LaunchedEffect
        } catch (e: HttpException){
            Log.e(TAG, "HttpException, unexpected response")
            return@LaunchedEffect
        }



        if(response.isSuccessful && response.body() != null){

            val text = response.body()!!.drinks[0].strDrink
            Log.d(TAG,text)
        }
    }

    //Text(text = text)







    /*
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();





     */



    
  


}