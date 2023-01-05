package com.example.drinkersjournal



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import kotlin.random.Random




@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewListScreen(){

    SetBackgroundImage()



    DisplayDrinkList()




}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayDrinkList(){


    LazyColumn() {
        itemsIndexed(DrinkersList.drinkList){ index, drink ->

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(100.dp)


            ) {
                GlideImage(
                    model = drink.strDrinkThumb.toString(),
                    contentDescription = "Drink image",
                    modifier = Modifier
                        .fillMaxHeight(1f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                        //.background(Color.Green)
                ) {
                    Text(text = drink.strDrink.toString(),
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            //.background(color = Color.Red)
                    )
                    Text(text = drink.ratingText.toString(),
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            //.background(color = Color.Green)
                            .padding(horizontal = 10.dp)
                            .fillMaxSize()
                    )

                }


                Text(text = drink.rating.toString(),
                    textAlign = TextAlign.Justify,
                    color = Color.White,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .fillMaxWidth())

            }







        }
    }


}



