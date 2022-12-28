package com.example.drinkersjournal

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import okio.IOException
import retrofit2.HttpException
import java.io.InputStream
import java.net.URL


const val TAG = "RandomDrinkScreen"
val text = mutableStateOf("No Drink Name")
val imageUrlStr = mutableStateOf("")



@OptIn(ExperimentalGlideComposeApi::class)
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
        val painter: Painter

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


            text.value = response.body()!!.drinks[0].strDrink
            imageUrlStr.value = response.body()!!.drinks[0].strDrinkThumb





        }

    }

    Text(text = text.value)
    
    GlideImage(
        model = imageUrlStr.value,
        contentDescription = "Picture of Random Drink",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp))





}





    
  

