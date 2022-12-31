package com.example.drinkersjournal

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.io.InputStream
import java.net.URL



var imageUrlStr = mutableStateOf("No Name")
var drinkName = mutableStateOf("No Name")






@Composable
fun RandomDrinkScreen () {





    // renders background image
    setBackgroundImage()

    // gets content, which then recomposes on screen
    retrieveRandomDrink()

    // sets up content to display
    setContent()








}

@OptIn(ExperimentalGlideComposeApi::class) //for glideImage
@Composable
fun setContent() {


    // Column Start
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        GlideImage(
            model = imageUrlStr.value,
            contentDescription = "Picture of Random Drink")

        Text(
            text = drinkName.value,
            color = Color.White,
            fontSize = 30.sp)

        Button(onClick = { retrieveRandomDrink() }) {
            Text(text = "Try Another")
        }
    }


}

@Composable
fun setBackgroundImage(){
    Image(
        painter = painterResource(id = R.drawable.ic_temporary_home_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}


fun retrieveRandomDrink(){



    CoroutineScope(Dispatchers.Main).launch {

        val response = try {
            RetrofitInstance.api.getRandomCocktail()
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {

            drinkName.value = response.body()!!.drinks[0].strDrink
            imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb

        }

    }


}







    
  

